import java.util.Random;

public class CarGateway {
    private MessageReceiver messageReceiver;
    private MessageSender messageSender;
    Random rnd = new Random();

    public CarGateway(){
        try{
            messageReceiver =  new MessageReceiver();
            messageSender = new MessageSender("PoliceCarStatusQueue");
            messageReceiver.carStartReceiving("policeCarQueue", sendUpdate(messageSender));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public StatusUpdateListener sendUpdate(MessageSender sender) {
       return () -> {
           try {
               sender.sendStatusUpdate("location");
           } catch (Exception e) {
               e.printStackTrace();
           }
       };
    }
}
