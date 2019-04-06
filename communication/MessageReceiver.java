import com.rabbitmq.client.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MessageReceiver {

    private String queueName;
    ConnectionFactory factory;
    public MessageReceiver(String queueName, String service) throws Exception{
        this.queueName = queueName;
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel =  connection.createChannel();
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 2000);
        args.put("x-dead-letter-exchange", "dlx");
        args.put("x-dead-letter-routing-key", service);
        channel.queueDeclare(queueName, false, false, false, args);
        System.out.println("[*] Waiting for messages. To Exit Press CTRL +C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            Random rnd = new Random();
            int n = rnd.nextInt(2);
            System.out.println(n);

            channel.basicQos(1);
            if(n == 1){
                channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
                System.out.println("[x] message rejected,  should go to dead letter channel please");
            } else{
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("[x] received '" + message + "'");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }


        };

        channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {});
    }
}
