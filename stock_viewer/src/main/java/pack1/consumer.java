package pack1;

public class consumer {

    public static void main (String [] args)  {
        kaf_prod_cons consumer_obj = new kaf_prod_cons();
        consumer_obj.KafConsumer("BANK");
    }
    
}
