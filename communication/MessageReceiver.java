import com.rabbitmq.client.*;

import java.util.HashMap;
import java.util.Map;

public class MessageReceiver {

    private String queueName;
    ConnectionFactory factory;
    public MessageReceiver(String queueName) throws Exception{
        this.queueName = queueName;
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel =  connection.createChannel();
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 2000);
        channel.queueDeclare(queueName, false, false, false, args);
        System.out.println("[*] Waiting for messages. To Exit Press CTRL +C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("[x] received '" + message + "'");

        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}
