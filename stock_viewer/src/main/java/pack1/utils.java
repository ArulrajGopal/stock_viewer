package pack1;

import org.json.JSONArray;
import org.json.JSONObject;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class utils {

        public void load_into_dydb (String table_name, String message){
                AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
                DynamoDB dynamoDb = new DynamoDB(client);

                long currentTimeMillis = System.currentTimeMillis();

                JSONArray result_jsonarr = new JSONArray(message);
                if (result_jsonarr.length() != 0){

                        JSONObject element =(JSONObject) result_jsonarr.get(0);

                        String symbol = element.getString("symbol");
                        float open = element.getFloat("open");
                        float dayHigh = element.getFloat("dayHigh");
                        float dayLow = element.getFloat("dayLow");
                        float lastPrice = element.getFloat("lastPrice");
                        float previousClose = element.getFloat("previousClose");
                        long totalTradedVolume = element.getInt("totalTradedVolume");
                        long totalTradedValue = element.getInt("totalTradedValue");
                        String lastUpdateTime = element.getString("lastUpdateTime");

                        Table table = dynamoDb.getTable(table_name);
                        table.putItem(new Item().withPrimaryKey("primary_id", currentTimeMillis)
                                                .with("symbol", symbol)
                                                .with("open_price", open)
                                                .with("day_high", dayHigh)
                                                .with("day_low", dayLow)
                                                .with("last_traded_price", lastPrice)
                                                .with("previous_close", previousClose)
                                                .with("total_traded_vol", totalTradedVolume)
                                                .with("total_traded_val", totalTradedValue)
                                                .with("last_updated_time", lastUpdateTime)
                                                );
                
                        System.out.println("Success");
                }
                else {System.out.println("no extract");}

        }


        public void convert_epoch_to_ist(long epoch_time) {

                Instant instant = Instant.ofEpochMilli(epoch_time);
                ZoneId istZone = ZoneId.of("Asia/Kolkata");
                String istTime = instant.atZone(istZone)
                                        .format(DateTimeFormatter
                                        .ofPattern("yyyy-MM-dd HH:mm:ss"));
                System.out.println("IST time: " + istTime);
        
        }

}



