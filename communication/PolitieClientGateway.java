public class PolitieClientGateway {

    private MessageReceiver messageReceiver;

    public PolitieClientGateway(){
        try{
            messageReceiver = new MessageReceiver("PolitieQueue", "Politie");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
