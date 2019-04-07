import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class MessageSender {


    private String exchangeName;
    private Connection connection;
    private Channel channel;
    ConnectionFactory factory = new ConnectionFactory();
    public MessageSender(String exchangeName){
        this.exchangeName = exchangeName;

        factory.setHost("localhost");

        try{
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException | TimeoutException e){
            e.printStackTrace();
        }
    }

    public void sendRoutedMessage(String messageText, String service){
        try{
            Map<String, Object> args = new HashMap<>();
            args.put("x-message-ttl", 2000);
            args.put("x-dead-letter-exchange", "dlx");
            args.put("x-dead-letter-routing-key", service);
            channel.queueDeclare(exchangeName, false, false, false, args);;
            channel.basicPublish("", exchangeName, null, messageText.getBytes("UTF-8"));
            System.out.println("[x] Sent '" + messageText + "'" + " to " + exchangeName);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(String service, String messageText){
        try{

            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
            channel.basicPublish(exchangeName, service, null , messageText.getBytes("UTF-8"));
            System.out.println("[x] Sent '" + service + "':'" + messageText + "'" + " to " +  exchangeName);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessageToCar(String message) throws Exception{
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 60000);
        args.put("x-dead-letter-exchange", "dlx-police-car");
        args.put("x-dead-letter-routing-key", "police-car");
        channel.queueDeclare(exchangeName, false, false, false, args);
        channel.basicPublish("", exchangeName, null, message.getBytes("UTF-8"));
        System.out.println("[x] Message was sent to " + exchangeName);
    }
}
