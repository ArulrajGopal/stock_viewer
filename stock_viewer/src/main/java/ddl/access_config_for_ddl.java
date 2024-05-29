package ddl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import org.json.JSONArray;
import org.json.JSONObject;



public class access_config_for_ddl {

    public JSONArray get_topics_list () throws IOException {

        String filePath = "stock_viewer/src/main/java/config.json"; 
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject jsonObject = new JSONObject(content);
        JSONObject result = jsonObject.getJSONObject("kafka_topics"); 

        JSONArray topics_array = new JSONArray();
        
        for (String key : result.keySet()) {
                  topics_array.put(result.get(key));
         }
        return topics_array;
     }

    public JSONArray get_tables_list () throws IOException {

        String filePath = "stock_viewer/src/main/java/config.json"; 
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject jsonObject = new JSONObject(content);
        JSONObject result = jsonObject.getJSONObject("dynamodb_table_names"); 

        JSONArray tbls_array = new JSONArray();
        
        for (String key : result.keySet()) {
            tbls_array.put(result.get(key));
         }
        return tbls_array;

    }



}





