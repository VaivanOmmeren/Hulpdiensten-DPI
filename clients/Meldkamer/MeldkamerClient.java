import java.util.Scanner;

public class MeldkamerClient {

    private MeldkamerClientGateway meldkamerClientGateway;

    public static void main(String[] args) {

        MeldkamerClient client = new MeldkamerClient();
    }

    public MeldkamerClient(){
        meldkamerClientGateway = new MeldkamerClientGateway();
    }
}
