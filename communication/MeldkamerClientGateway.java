public class MeldkamerClientGateway {

    private MessageRouter binnenkomendeMeldingen;
    public MeldkamerClientGateway(){

        try{
            binnenkomendeMeldingen = new MessageRouter();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
