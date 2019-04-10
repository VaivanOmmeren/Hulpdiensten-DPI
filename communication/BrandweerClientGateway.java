import com.rabbitmq.client.DeliverCallback;

import java.util.Random;

@SuppressWarnings("Duplicates")
public class BrandweerClientGateway {

    private MessageReceiver messageReceiver;
    private MessageReceiver statusUpdateReceiver;
    private DeadLetter deadLetter;
    private MessageSender messageSender;

    public BrandweerClientGateway(){
        try{
            messageReceiver = new MessageReceiver("BrandweerQueue", "Brandweer");
            messageReceiver.serviceStartReceiving(handleMessages());
            statusUpdateReceiver = new MessageReceiver();
            statusUpdateReceiver.listenToStatusUpdates("BrandweerCarStatusQueue", handleStatusChange());
            deadLetter = new DeadLetter();
            deadLetter.deadLetterService("dlx-brandweer-car", "brandweer-car");

            messageSender = new MessageSender("brandweerCarQueue");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public DeliverCallback handleMessages(){
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            Random rnd = new Random();
            int n = rnd.nextInt(10);

            if (n == 1) {
                messageReceiver.channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
                System.out.println("[x] message rejected,  should go to dead letter channel please");
            } else {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("[x] received '" + message + "'");
                messageReceiver.channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                try {
                    messageSender.sendMessageToCar(message, "dlx-brandweer-car", "brandweer-car");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        return deliverCallback;
    }

    public DeliverCallback handleStatusChange(){
        DeliverCallback callback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("[Status] Received status message: " + message + " updating car status");
            statusUpdateReceiver.channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

        };
        return callback;
    }
}
