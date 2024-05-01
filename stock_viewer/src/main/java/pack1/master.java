package pack1;


class MyThread extends Thread {
    private String parameter;
    utils obj = new utils();

    public MyThread(String parameter) {
        this.parameter = parameter;
    }

    public void run() {
        System.out.println(parameter);
        // if (parameter == "producer") { producermethod(); }
        // else if (parameter == "consumer"){ consumermethod(); }

    }

    // private void consumermethod() {obj.KafConsumer("finservice");}
    // private void producermethod() {obj.KafProducer("finservice");}

}

public class master {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread("producer#finservice");
        MyThread thread2 = new MyThread("consumer#finservice");
        MyThread thread3 = new MyThread("producer#pharma");
        MyThread thread4 = new MyThread("consumer#pharma");


        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
