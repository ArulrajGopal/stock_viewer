package pack1;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        System.out.println("Connection working!");


        fetchstockdetails obj = new fetchstockdetails();
        System.out.println(obj.fetchstockDetails_for_sector("ENERGY"));
    }
}


