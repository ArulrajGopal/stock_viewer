// package pack1;


// class MyThread extends Thread {
//     private String parameter;
//     utils obj = new utils();

//     public MyThread(String parameter) {
//         this.parameter = parameter;
//     }

//     public void run() {

//         if (parameter == "producer_finservice") { obj.KafProducer("finservice"); }
//         else if (parameter == "consumer_finservice"){ obj.KafConsumer("finservice");}
//         else if (parameter == "producer_pharma"){ obj.KafProducer("pharma");}
//         else if (parameter == "consumer_pharma"){ obj.KafConsumer("pharma");}
//         else if (parameter == "producer_energy"){ obj.KafProducer("energy");}
//         else if (parameter == "consumer_energy"){ obj.KafConsumer("energy");}
//         else if (parameter == "producer_fmcg"){ obj.KafProducer("fmcg");}
//         else if (parameter == "consumer_fmcg"){ obj.KafConsumer("fmcg");}

//     }

// }

// public class main_file {
//     public static void main(String[] args) {
//         MyThread thread1 = new MyThread("producer_finservice");
//         MyThread thread2 = new MyThread("consumer_finservice");
//         MyThread thread3 = new MyThread("producer_pharma");
//         MyThread thread4 = new MyThread("consumer_pharma");
//         MyThread thread5 = new MyThread("producer_energy");
//         MyThread thread6 = new MyThread("consumer_energy");
//         MyThread thread7 = new MyThread("producer_fmcg");
//         MyThread thread8 = new MyThread("consumer_fmcg");


//         thread1.start();
//         thread2.start();
//         thread3.start();
//         thread4.start();
//         thread5.start();
//         thread6.start();
//         thread7.start();
//         thread8.start();
//     }
// }
