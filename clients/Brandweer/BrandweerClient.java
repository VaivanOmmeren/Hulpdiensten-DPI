public class BrandweerClient {

    private BrandweerClientGateway brandweerClientGateway;
    public static void main(String[] args){
        BrandweerClient client =  new BrandweerClient();
    }

    public BrandweerClient(){
        brandweerClientGateway = new BrandweerClientGateway();
    }
}
