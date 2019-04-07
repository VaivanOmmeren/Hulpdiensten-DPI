public class MeldkamerClientGateway {

    private MessageRouter binnenkomendeMeldingen;
    private DeadLetter deadLetter;
    public MeldkamerClientGateway(){

        try{
            binnenkomendeMeldingen = new MessageRouter();
            deadLetter = new DeadLetter();
            deadLetter.deadLetterMeldkamer();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
