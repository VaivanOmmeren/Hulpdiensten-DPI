import com.rabbitmq.client.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MessageReceiver {

    public String queueName;
    private String service;
    ConnectionFactory factory;
    Connection connection;
    public Channel channel;
    public MessageReceiver(String queueName, String service){
        this.queueName = queueName;
        this.service = service;
    }

    public MessageReceiver(){
    }

    public void serviceStartReceiving(DeliverCallback callback) throws Exception{
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel =  connection.createChannel();
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 2000);
        args.put("x-dead-letter-exchange", "dlx");
        args.put("x-dead-letter-routing-key", service);
        channel.queueDeclare(queueName, false, false, false, args);
        System.out.println("[*] Waiting for messages. To Exit Press CTRL +C");

        channel.basicConsume(queueName, false, callback, consumerTag -> {});
    }

    public void listenToStatusUpdates(String statusQueueName, DeliverCallback callback) throws Exception{
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDeclare(statusQueueName, false, false, false, null);
        System.out.println("[*] waitin for status messages. To Exit Press CTRL + C");

        channel.basicConsume(statusQueueName, false, callback, consumerTag -> {});
    }

    public void carStartReceiving(String carQueueName) throws Exception {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 60000);
        args.put("x-dead-letter-exchange", "dlx-police-car");
        args.put("x-dead-letter-routing-key", "police-car");
        channel.queueDeclare(carQueueName, false, false, false, args);
        System.out.println("[*] Car waiting for messages. To Exit Press CTRL + C");

        channel.basicQos(1);
        DeliverCallback deliverCallback = (consumerTag, delivery) ->{
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("[*] Message received: '" + message + "'");

            try {
                Thread.sleep(20000);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            } catch (InterruptedException e) {
                e.printStackTrace();
                channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
            }

        };

        channel.basicConsume(carQueueName, false, deliverCallback, consumerTag -> {});
    }
}
