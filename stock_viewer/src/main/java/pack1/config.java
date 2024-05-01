package pack1;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class config {

    public static String get_sector_code(String sector) {
        String json = "{\"pharma\": \"PHARMA\", \"energy\": \"ENERGY\", \"fmcg\": \"FMCG\", \"finservice\": \"FIN%20SERVICE\"}";

        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String result = jsonObject.get(sector).getAsString();
        return result;
 
    }
}


