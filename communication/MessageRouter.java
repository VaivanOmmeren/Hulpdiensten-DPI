import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class MessageRouter {

    private static final String EXCHANGE_NAME = "melding";

    private MessageSender politieSender;
    private MessageSender ambulanceSender;
    private MessageSender brandweerSender;

    public MessageRouter() throws Exception {

        politieSender = new MessageSender("PolitieQueue");
        ambulanceSender = new MessageSender("AmbulanceQueue");
        brandweerSender = new MessageSender("BrandweerQueue");

        String[] services = new String[]{"Politie", "Ambulance", "Brandweer"};
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();


        for (String service : services) {
            channel.queueBind(queueName, EXCHANGE_NAME, service);
        }

        System.out.println("[*] Waiting for messages. Press CTRL + C to exit");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("[x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");

            String service = delivery.getEnvelope().getRoutingKey();

            switch (service) {
                case "Politie":
                    politieSender.sendRoutedMessage(message, "Politie");
                    break;
                case "Ambulance":
                    ambulanceSender.sendRoutedMessage(message, "Ambulance");
                    break;
                case "Brandweer":
                    brandweerSender.sendRoutedMessage(message, "Brandweer");
                    break;
            }
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
