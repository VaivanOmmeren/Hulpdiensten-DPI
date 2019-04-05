public class AmbulanceClient {

    private AmbulanceClientGateway ambulanceClientGateway;
    public static void main(String args[]) {
        AmbulanceClient client = new AmbulanceClient();
    }

    public AmbulanceClient(){
        ambulanceClientGateway = new AmbulanceClientGateway();
    }
}
