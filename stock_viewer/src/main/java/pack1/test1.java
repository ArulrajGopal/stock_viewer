package pack1;


class MyThread extends Thread {
    private int parameter;

    public MyThread(int parameter) {
        this.parameter = parameter;
    }

    public void run() {
        if (parameter % 2 == 0) {
            evenMethod();
        } else {
            oddMethod();
        }
    }

    private void evenMethod() {
        System.out.println("Even thread with parameter " + parameter);
    }

    private void oddMethod() {
        System.out.println("Odd thread with parameter " + parameter);
    }
}

public class test1 {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread(2);
        MyThread thread2 = new MyThread(3);

        thread1.start();
        thread2.start();

        // Main thread continues independently
        System.out.println("Main thread continues...");
    }
}
