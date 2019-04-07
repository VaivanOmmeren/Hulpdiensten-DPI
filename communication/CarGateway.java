public class CarGateway {
    private MessageReceiver messageReceiver;

    public CarGateway(){
        try{
            messageReceiver =  new MessageReceiver();
            messageReceiver.carStartReceiving("policeCarQueue");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
