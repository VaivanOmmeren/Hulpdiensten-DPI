public class CarGateway {
    private MessageReceiver messageReceiver;

    public CarGateway(){
        try{
            messageReceiver =  new MessageReceiver();
            messageReceiver.carStartReceiving("car1");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
