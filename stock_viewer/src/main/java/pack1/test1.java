package pack1;
import org.json.JSONArray;
import org.json.JSONObject;

public class test1 {
    public static void main(String[] args) {
        utils obj = new utils();
        String message = obj.fetchStockDetails("PHARMA");

        JSONArray result_jsonarr = new JSONArray(message);

        JSONObject element = (JSONObject) result_jsonarr.get(0);

        String symbol = element.getString("symbol");
        System.out.println(symbol);

        System.out.println(message);
        System.out.println(result_jsonarr);
        System.out.println(element);
        System.out.println(symbol);

    }
    
}

