package pack1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import org.json.JSONArray;
import org.json.JSONObject;



public class access_config {

    public JSONArray get_identifier_list (String sector) throws IOException {

        String filePath = "stock_viewer/src/main/java/pack1/config.json"; 
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject jsonObject = new JSONObject(content);
        JSONArray result = jsonObject.getJSONObject("identifier_list").getJSONArray(sector);               
        return result;
     }


     
     public String get_topic_name (String sector) throws IOException {
        String filePath = "stock_viewer/src/main/java/pack1/config.json"; 
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject jsonObject = new JSONObject(content);
        String result = jsonObject.getJSONObject("kafka_topics").getString(sector);
        return result;

     }
}





