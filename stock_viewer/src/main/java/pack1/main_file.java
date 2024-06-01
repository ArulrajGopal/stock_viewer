package pack1;


class MyThread extends Thread {
    private String parameter;
    static kaf_prod_cons obj = new kaf_prod_cons();

    public MyThread(String parameter) {
        this.parameter = parameter;
    }


    public static void run_kafka(String Kafka_component, String sector) {
        if (Kafka_component == "producer") {
            obj.KafProducer(sector);
        }
        else if (Kafka_component == "consumer") {
            obj.KafProducer(sector);
        }
    }



    public void run() {

        if (parameter == "producer_bank") { obj.KafProducer("finservice"); }
        else if (parameter == "consumer_bank"){ obj.KafConsumer("finservice");}
        else if (parameter == "producer_energy"){ obj.KafProducer("pharma");}
        else if (parameter == "consumer_pharma"){ obj.KafConsumer("pharma");}
        else if (parameter == "producer_energy"){ obj.KafProducer("energy");}
        else if (parameter == "consumer_energy"){ obj.KafConsumer("energy");}
        else if (parameter == "producer_fmcg"){ obj.KafProducer("fmcg");}
        else if (parameter == "consumer_fmcg"){ obj.KafConsumer("fmcg");}

    }

}




public class main_file {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread("producer_bank");
        MyThread thread2 = new MyThread("consumer_bank");
        MyThread thread3 = new MyThread("producer_energy");
        MyThread thread4 = new MyThread("consumer_energy");
        MyThread thread5 = new MyThread("producer_fmcg");
        MyThread thread6 = new MyThread("consumer_fmcg");
        MyThread thread7 = new MyThread("producer_healthcare");
        MyThread thread8 = new MyThread("consumer_healthcare");


        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
    }
}
