package pack1;

import java.io.IOException;

import org.json.JSONArray;


public class test1 {
        public static void main(String[] args) throws IOException  {
            fetchstockdetails obj = new fetchstockdetails();
            JSONArray result = obj.fetchstockDetails_for_sector("HEALTHCARE");
            System.out.println(result);

    }
    
}
