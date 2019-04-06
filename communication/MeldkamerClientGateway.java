public class MeldkamerClientGateway {

    private MessageRouter binnenkomendeMeldingen;
    private DeadLetter deadLetter;
    public MeldkamerClientGateway(){

        try{
            binnenkomendeMeldingen = new MessageRouter();
            deadLetter = new DeadLetter();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
