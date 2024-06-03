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

    public JSONArray get_sns_topic () throws IOException {

        String filePath = "stock_viewer/src/main/java/config.json"; 
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject jsonObject = new JSONObject(content);
        JSONObject result = jsonObject.getJSONObject("sns_topic_names"); 

        JSONArray tbls_array = new JSONArray();
        
        for (String key : result.keySet()) {
            tbls_array.put(result.get(key));
         }
        return tbls_array;

    }

    public JSONArray get_lambda_functions() throws IOException {

        String filePath = "stock_viewer/src/main/java/config.json"; 
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject jsonObject = new JSONObject(content);
        JSONObject result = jsonObject.getJSONObject("lambda_func_names"); 

        JSONArray tbls_array = new JSONArray();
        
        for (String key : result.keySet()) {
            tbls_array.put(result.get(key));
         }
        return tbls_array;

    }

    public String get_mail_id() throws IOException {

        String filePath = "stock_viewer/src/main/java/config.json"; 
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject jsonObject = new JSONObject(content);
        String result = jsonObject.getString("sns_subscribe_mail_id"); 

        return result;

    }

    public String get_iam_role() throws IOException {

        String filePath = "stock_viewer/src/main/java/config.json"; 
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject jsonObject = new JSONObject(content);
        String result = jsonObject.getString("iam_role_for_lambda"); 

        return result;

    }



}





