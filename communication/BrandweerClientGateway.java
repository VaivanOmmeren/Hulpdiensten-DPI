public class BrandweerClientGateway {

    private MessageReceiver messageReceiver;

    public BrandweerClientGateway(){
        try{
            messageReceiver = new MessageReceiver("BrandweerQueue");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
