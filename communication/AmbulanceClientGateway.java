public class AmbulanceClientGateway {

    private MessageReceiver messageReceiver;

    public AmbulanceClientGateway(){
        try{
            messageReceiver = new MessageReceiver("AmbulanceQueue", "ambulance");
        } catch (Exception e ){
            e.printStackTrace();
        }
    }
}
