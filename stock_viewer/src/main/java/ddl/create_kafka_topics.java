package ddl;

import pack1.kaf_prod_cons;

public class create_kafka_topics {
    public static void main(String[] args) {
        kaf_prod_cons obj = new kaf_prod_cons();

        obj.KafProducer("HEALTHCARE");
    }
    
}
