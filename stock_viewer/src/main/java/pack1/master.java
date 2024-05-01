package pack1;


class MyThread extends Thread {
    private String parameter;
    utils obj = new utils();

    public MyThread(String parameter) {
        this.parameter = parameter;
    }

    public void run() {
        String[] parts = parameter.split("#", 2);
        String system_type = parts[0];
        String sector_variable = parts[1];

        if (system_type == "producer") { 
            // producermethod(sector_variable); 
            System.out.println(sector_variable);
        }
        else if (system_type == "consumer"){ 
            // consumermethod(sector_variable);
            System.out.println(sector_variable);
        }

    }

    // private void producermethod(String sector_variable) {obj.KafProducer(sector_variable);}
    // private void consumermethod(String sector_variable) {obj.KafConsumer(sector_variable);}
    

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
