import java.util.Scanner;

public class MeldingClient {

    private MeldingClientGateway meldingClientGateway;
    public static void main(String[] args) throws Exception{
        MeldingClient client = new MeldingClient();
    }

    public MeldingClient() throws Exception{
        meldingClientGateway = new MeldingClientGateway();
        Scanner in = new Scanner(System.in);


        while(true){
            System.out.println("What service is this message directed at:");
            String service = in.next();
            System.out.println("What is the message");
            String message = in.next();
            meldingClientGateway.sendMelding(service, message);
        }
    }
}
