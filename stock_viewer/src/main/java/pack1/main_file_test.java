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


public class main_file_test {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread("producer", "BANK");
        MyThread thread2 = new MyThread("consumer","BANK");



        thread1.start();
        thread2.start();
    }
}
