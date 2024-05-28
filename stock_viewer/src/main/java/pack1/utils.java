package pack1;

import org.json.JSONArray;
import org.json.JSONObject;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.Callback;
import java.util.Properties;
import java.util.Random;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.CooperativeStickyAssignor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays; 


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



        public void KafProducer (String topic){
        
                // create Producer Properties & Setting up properties
                Properties properties = new Properties();
                properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
                properties.setProperty("key.serializer", StringSerializer.class.getName());
                properties.setProperty("value.serializer", StringSerializer.class.getName());
        
                // create the Producer
                KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
                Random random = new Random();
                fetchstockdetails obj_Fetchstockdetails = new fetchstockdetails();

                try {
                        while(true) {

                                System.out.println("Producing");

                                String key = String.valueOf(random.nextInt(3));
                                String message = obj_Fetchstockdetails.fetchStockDetails();
                                
                                // create a Producer Record
                                ProducerRecord <String, String> producerRecord = new ProducerRecord<>(topic, key, message);


                                // producer.send(producerRecord);
                                producer.send(producerRecord, new Callback() {
                                @Override
                                public void onCompletion(RecordMetadata metadata, Exception e) {
                                        // executes every time a record successfully sent or an exception is thrown
                                        if (e == null) {
                                        // the record was successfully sent
                                        System.out.println(
                                                "Received new metadata \n" +
                                                "Topic: " + metadata.topic() + "|" +  
                                                "Partition: " + metadata.partition() + "|" + 
                                                "Offset: " + metadata.offset() + "|" +
                                                "Key: " + key + "|" +  
                                                "Timestamp: " + metadata.timestamp());
                                        } else {
                                        System.out.println("Error while producing"+e);
                                        }
                                } } );
                        
                                try {Thread.sleep(2000);} 
                                catch (InterruptedException e) {e.printStackTrace();}

                        }
                }


                catch (Exception e) { 
                        System.out.println("Unexpected exception in the consumer: "+ e);
                } 


                finally {
                        producer.flush();
                        producer.close();
                        System.out.println("The consumer is now gracefully shut down");
                }
        
        }


        public void KafConsumer(String topic) {

                String groupId = topic+"_group_id";
         
                // create Producer Properties
                Properties properties = new Properties();
        
                // Setting up properties
                properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
                properties.setProperty("key.deserializer", StringDeserializer.class.getName());
                properties.setProperty("value.deserializer", StringDeserializer.class.getName());
                properties.setProperty("group.id", groupId);
                properties.setProperty("auto.offset.reset", "earliest");
                properties.setProperty("partition.assignment.strategy", CooperativeStickyAssignor.class.getName());
            
                // create a consumer
                KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
                utils obj = new utils();
                
                try {
                consumer.subscribe(Arrays.asList(topic));
                while (true) {
        
                        System.out.println("Polling");
        
                        ConsumerRecords<String, String> records =  consumer.poll(Duration.ofMillis(1000));
        
                        for (ConsumerRecord<String, String> record: records) {
                        
                        obj.load_into_dydb (topic, record.value());
                        // System.out.println("Key: " + record.value());
                        System.out.println("Key: " + record.key());
                        System.out.println("Partition: " + record.partition());
                        System.out.println("Offset: " + record.offset());
                        }
                }
                }  
                catch (Exception e) { System.out.println("Unexpected exception in the consumer: "+ e);} 
                finally {consumer.close(); System.out.println("The consumer is now gracefully shut down");}
                
                
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



