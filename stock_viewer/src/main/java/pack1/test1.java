public class MyThread extends Thread {
    private int count; // Parameter to be passed

    public MyThread(int count) {
        this.count = count;
    }

    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) {
        MyThread thread1 = new MyThread(5); // Pass parameter 5 to the thread
        MyThread thread2 = new MyThread(3); // Pass parameter 3 to the thread

        thread1.start();
        thread2.start();
    }
}
