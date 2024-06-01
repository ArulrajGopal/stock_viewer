package pack1;


class MyThread extends Thread {
    private String parameter;
    private String sector;
    static kaf_prod_cons obj = new kaf_prod_cons();

    public MyThread(String parameter, String sector) {
        this.parameter = parameter;
        this.sector = sector;
    }



    public void run() {

        if (parameter == "producer") {
            obj.KafProducer(sector);
        }
        else if (parameter == "consumer") {
            obj.KafConsumer(sector);
        }

    }

}


public class main_file {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread("producer", "BANK");
        MyThread thread2 = new MyThread("consumer","BANK");
        MyThread thread3 = new MyThread("producer","ENERGY");
        MyThread thread4 = new MyThread("consumer","ENERGY");
        MyThread thread5 = new MyThread("producer","FMCG");
        MyThread thread6 = new MyThread("consumer","FMCG");
        MyThread thread7 = new MyThread("producer","HEALTHCARE");
        MyThread thread8 = new MyThread("consumer","HEALTHCARE");


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
