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

                JSONArray result_jsonarr = new JSONArray(message);
                
                if (result_jsonarr.length() != 0){

                        for (Object item: result_jsonarr) {

                                JSONObject element = (JSONObject) item;

                                long currentTimeMillis = System.currentTimeMillis();

                                String symbol = element.getString("symbol");
                                float open = element.getFloat("open");
                                float day_high = element.getFloat("dayHigh");
                                float day_low = element.getFloat("dayLow");
                                float last_price = element.getFloat("lastPrice");
                                float previous_close = element.getFloat("previousClose");
                                long total_traded_volume = element.getInt("totalTradedVolume");
                                long total_traded_value = element.getInt("totalTradedValue");
                                String last_update_time = element.getString("lastUpdateTime");
                                String identifier = element.getString("identifier");
                                float change = element.getFloat("change");
                                float year_low = element.getFloat("yearLow");
                                float year_high = element.getFloat("yearHigh");
                                float per_change_day = element.getFloat("pChange");
                                float per_change_mnth = element.getFloat("perChange30d");
                                float per_change_year = element.getFloat("perChange365d");

                               
        
                                Table table = dynamoDb.getTable(table_name);
                                table.putItem(new Item().withPrimaryKey("primary_id", currentTimeMillis)
                                                        .with("symbol", symbol)
                                                        .with("open_price", open)
                                                        .with("day_high", day_high)
                                                        .with("day_low", day_low)
                                                        .with("last_traded_price", last_price)
                                                        .with("previous_close", previous_close)
                                                        .with("total_traded_vol", total_traded_volume)
                                                        .with("total_traded_val", total_traded_value)
                                                        .with("last_updated_time", last_update_time)
                                                        .with("identifier", identifier)
                                                        .with("change", change)
                                                        .with("year_low", year_low)
                                                        .with("year_high", year_high)
                                                        .with("per_change_day", per_change_day)
                                                        .with("per_change_mnth", per_change_mnth)
                                                        .with("per_change_year", per_change_year)
                                                        );
                        
                                System.out.println("Success");

                        }


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



