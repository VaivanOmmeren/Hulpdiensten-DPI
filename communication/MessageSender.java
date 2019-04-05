import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
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

    public void sendMessage(String messageText){
        try{
            channel.queueDeclare(exchangeName, false, false, false, null);;
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
}
