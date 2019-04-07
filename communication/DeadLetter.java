import com.rabbitmq.client.*;

public class DeadLetter {
    ConnectionFactory factory;
    Connection connection;
    Channel channel;

    public DeadLetter() throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        connection = factory.newConnection();

        channel = connection.createChannel();


    }

    public void deadLetterMeldkamer() throws Exception {
        String[] services = new String[]{"Politie", "Ambulance", "Brandweer"};
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

    public void deadLetterService(String serviceName, String routingKey) throws Exception{

        channel.exchangeDeclare(serviceName, BuiltinExchangeType.DIRECT);
        String queueName =  channel.queueDeclare().getQueue();

        channel.queueBind(queueName, serviceName, routingKey);

        System.out.println("[*] Waiting for police car dead letters. To exit press CTRL + C");

        DeliverCallback callback = (consumerTag, delivery) ->{
            String message =  new String(delivery.getBody(), "UTF-8");
            System.out.println("Receveid a message '" + message + "'");
            System.out.println("[Reason] " + delivery.getProperties().getHeaders().get("x-death").toString());
        };

        channel.basicConsume(queueName, true, callback, consumerTag ->{});
    }
}

