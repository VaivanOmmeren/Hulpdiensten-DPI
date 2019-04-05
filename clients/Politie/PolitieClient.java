public class PolitieClient {

    private PolitieClientGateway politieClientGateway;
    public static void main(String[] args) {
        PolitieClient client = new PolitieClient();
    }

    public PolitieClient(){
        politieClientGateway = new PolitieClientGateway();
    }
}
