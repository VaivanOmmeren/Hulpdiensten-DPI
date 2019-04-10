import java.util.Random;

public class CarGateway {
    private MessageReceiver messageReceiver;
    private MessageSender messageSender;
    Random rnd = new Random();

    public CarGateway(){
        try{
            messageReceiver =  new MessageReceiver();
            messageReceiver.carStartReceiving("policeCarQueue");
            messageSender = new MessageSender("PoliceCarStatusQueue");

            while(true) {
                messageSender.sendStatusUpdate("location");
                Thread.sleep(20000);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
