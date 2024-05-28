package pack1;

import java.io.IOException;

import org.json.JSONArray;

public class test1 {
        public static void main(String[] args) throws IOException {

        access_config obj1 = new access_config();
        System.out.println(obj1.get_identifier_list("FMCG"));

        fetchstockdetails obj2 = new fetchstockdetails();
        String str_output = obj2.fetchstockDetails_direct();
        JSONArray result_jsonarr = new JSONArray(str_output);
        System.out.println(result_jsonarr);

    }
    
}
