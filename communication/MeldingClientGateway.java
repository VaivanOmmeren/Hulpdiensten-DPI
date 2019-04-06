public class MeldingClientGateway {

    private MessageSender messageToMeldkamer;

    public MeldingClientGateway() throws Exception {
        messageToMeldkamer = new MessageSender("melding");
    }

    public void sendMelding(String service, String message){
        messageToMeldkamer.sendMessage(service, message);
    }

}
