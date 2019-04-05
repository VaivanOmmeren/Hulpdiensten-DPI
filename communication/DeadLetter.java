import com.rabbitmq.client.*;

public class DeadLetter {

    public DeadLetter() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare("dlx", BuiltinExchangeType.DIRECT);
        channel.queueDeclare("dl", false, false, false, null);
        String result = channel.queueDeclare().getQueue();

        channel.queueBind("dl",  "dlx", result);

        System.out.println("[*] Waiting for dead-letters. To exit press CTRL+C");

        DeliverCallback callback = ((consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("[*] Received message " + message);
            System.out.println("[Reason]" + delivery.getProperties().getHeaders().get("x-death").toString());
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), true);
        });

        channel.basicConsume("dl", true, callback, consumerTag -> {
        });
    }
}

