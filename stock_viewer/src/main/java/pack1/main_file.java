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

        System.out.println(system_type);
        System.out.println(sector_variable);


        // if (system_type == "producer" && sector_variable =="finservice") { 
        //     obj.KafProducer("finservice");
        // }
        // else if (system_type == "producer" && sector_variable =="finservice"){ 
        //     obj.KafConsumer("finservice");
        // }
        // else if (system_type == "producer" && sector_variable =="pharma"){ 
        //     obj.KafProducer("pharma");
        // }
        // else if (system_type == "consumer" && sector_variable =="pharma"){ 
        //     obj.KafConsumer("pharma");
        // }

    }

}

public class main_file {
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
