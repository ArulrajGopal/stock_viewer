package pack1;

public class test1 {
    public static void main(String[] args) {
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println("Current timestamp in milliseconds: " + currentTimeMillis);
        utils obj = new utils();
        obj.convert_epoch_to_ist(currentTimeMillis);

    }
}

