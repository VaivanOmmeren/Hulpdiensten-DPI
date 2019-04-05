public class MeldingClientGateway {

    private MessageSender messageToMeldkamer;
    private DeadLetter deadLetter;

    public MeldingClientGateway() throws Exception {
        messageToMeldkamer = new MessageSender("melding");
        deadLetter = new DeadLetter();

    }

    public void sendMelding(String service, String message){
        messageToMeldkamer.sendMessage(service, message);
    }

}
