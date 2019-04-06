import com.rabbitmq.client.*;

public class DeadLetter {

    public DeadLetter() throws Exception {
        String[] services = new String[]{"Politie", "Ambulance", "Brandweer"};
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare("dlx", BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();
        System.out.println("Dead letter channel name " + queueName);

        for(String service: services){
            channel.queueBind(queueName, "dlx", service);
        }

        System.out.println("[*] Waiting for dead-letters. To exit press CTRL+C");

        DeliverCallback callback = ((consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("[*] Received message " + message);
            System.out.println("[Reason]" + delivery.getProperties().getHeaders().get("x-death").toString());
        });

        channel.basicConsume(queueName, true, callback, consumerTag -> {
        });
    }
}

