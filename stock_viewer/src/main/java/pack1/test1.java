package pack1;
import java.io.IOException;

import org.json.JSONArray;

public class test1 {

    public static void main (String [] args) {
        fetchstockdetails obj = new fetchstockdetails();

        try {
            JSONArray result = obj.fetchstockDetails_for_sector("BANK");
            System.out.println(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}



