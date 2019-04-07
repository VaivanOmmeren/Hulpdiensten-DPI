public class AmbulanceClientGateway {

    private MessageReceiver messageReceiver;

    public AmbulanceClientGateway(){
        try{
            messageReceiver = new MessageReceiver("AmbulanceQueue", "ambulance");
//            messageReceiver.serviceStartReceiving();
        } catch (Exception e ){
            e.printStackTrace();
        }
    }
}
