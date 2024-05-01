package pack1;


class MyThread extends Thread {
    private String parameter;

    public MyThread(String parameter) {
        this.parameter = parameter;
    }

    public void run() {
        if (parameter == "producer") { producermethod(); }
        else if (parameter == "consumer"){ consumermethod(); }

    }

    private void consumermethod() {
        utils obj = new utils();
        obj.KafConsumer("finservice");
    }

    private void producermethod() {
        utils obj = new utils();
        obj.KafProducer("finservice");
    }
}

public class master {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread("producer");
        MyThread thread2 = new MyThread("consumer");

        thread1.start();
        thread2.start();
    }
}
