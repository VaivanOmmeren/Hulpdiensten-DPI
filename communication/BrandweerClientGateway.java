public class BrandweerClientGateway {

    private MessageReceiver messageReceiver;

    public BrandweerClientGateway(){
        try{
            messageReceiver = new MessageReceiver("BrandweerQueue", "Brandweer");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
