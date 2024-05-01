package pack1;

import org.json.JSONObject;

// variables = ["PHARMA","ENERGY","FMCG","FIN%20SERVICE"]

public class test1 {

    public static void main(String[] args) {
        utils obj = new utils();
        String message = obj.fetchStockDetails("FIN%20SERVICE");
   
        JSONObject element = (JSONObject) obj.StrToJsonConvert(message);

        String symbol = element.getString("symbol");

        System.out.println(message);
        System.out.println(element);
        System.out.println(symbol);

    }

    
    
}
