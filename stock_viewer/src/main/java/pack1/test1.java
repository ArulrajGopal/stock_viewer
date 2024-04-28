package pack1;

import org.json.JSONObject;

public class test1 {
    public static void main(String[] args) {
        utils obj = new utils();
        String message = obj.fetchStockDetails("PHARMA");
        JSONObject element = (JSONObject) obj.StrToJsonConvert(message);

        String symbol = element.getString("symbol");
        float open = element.getFloat("open");
        float dayHigh = element.getFloat("dayHigh");
        float dayLow = element.getFloat("dayLow");
        float lastPrice = element.getFloat("lastPrice");
        float previousClose = element.getFloat("previousClose");
        long totalTradedVolume = element.getInt("totalTradedVolume");
        long totalTradedValue = element.getInt("totalTradedValue");
        String lastUpdateTime = element.getString("lastUpdateTime");



        System.out.println(symbol);
        System.out.println(open);
        System.out.println(dayHigh);
        System.out.println(dayLow);
        System.out.println(lastPrice);
        System.out.println(previousClose);
        System.out.println(totalTradedVolume);
        System.out.println(totalTradedValue);
        System.out.println(lastUpdateTime);
        

    }
    
}

