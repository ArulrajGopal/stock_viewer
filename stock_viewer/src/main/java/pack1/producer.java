package pack1;

public class producer {

    public static void main (String [] args) {
        kaf_prod_cons producer_obj = new kaf_prod_cons();
        producer_obj.KafProducer("BANK");
    }
    
}
