package pack1;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
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
import java.util.Arrays; 



public class utils {

        public String fetchStockDetails (Object sector) {

                String suffix = "NIFTY%20"+sector+"&Identifier=NIFTY%20"+sector;
                String uri = String.format("https://latest-stock-price.p.rapidapi.com/price?Indices=%s", suffix);

                try {
                    HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .header("X-RapidAPI-Key", "23a945bde0msha8c00c2faa561f5p119d77jsnea9895e0d88d")
                        .header("X-RapidAPI-Host", "latest-stock-price.p.rapidapi.com")
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();

                    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                     return response.body(); }

                catch (IOException | InterruptedException e) {e.printStackTrace(); }

                return null; }



        public Object StrToJsonConvert (String jsonString){

                JSONArray result_jsonarr = new JSONArray(jsonString);
        
                Object element = result_jsonarr.get(0);
                return element;

                }



        public void KafProducer (String topic, String sector){
        
                // create Producer Properties & Setting up properties
                Properties properties = new Properties();
                properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
                properties.setProperty("key.serializer", StringSerializer.class.getName());
                properties.setProperty("value.serializer", StringSerializer.class.getName());
        
                // create the Producer
                KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
                Random random = new Random();
                utils obj = new utils();

                try {
                        while(true) {

                                System.out.println("Producing");

                                String key = String.valueOf(random.nextInt(3));
                                String message = obj.fetchStockDetails(sector);
                                
                                // create a Producer Record
                                ProducerRecord <String, String> producerRecord = new ProducerRecord<>(topic, key, message);


                                producer.send(producerRecord);
                                // producer.send(producerRecord, new Callback() {
                                // @Override
                                // public void onCompletion(RecordMetadata metadata, Exception e) {
                                //         // executes every time a record successfully sent or an exception is thrown
                                //         if (e == null) {
                                //         // the record was successfully sent
                                //         System.out.println(
                                //                 "Received new metadata \n" +
                                //                 "Topic: " + metadata.topic() + "|" +  
                                //                 "Partition: " + metadata.partition() + "|" + 
                                //                 "Offset: " + metadata.offset() + "|" +
                                //                 "Key: " + key + "|" +  
                                //                 "Timestamp: " + metadata.timestamp());
                                //         } else {
                                //         System.out.println("Error while producing"+e);
                                //         }
                                // } } );
                        
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


        public void KafConsumer(String topic, String groupId) {
         
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

            
                    // get a reference to the main thread
                    final Thread mainThread = Thread.currentThread();
            
                    Runtime.getRuntime().addShutdownHook(new Thread() {
                        public void run() {
                            System.out.println("Detected a shutdown, let's exit by calling consumer.wakeup()...");
                            consumer.wakeup();
            
                            try { mainThread.join(); }
                            catch (InterruptedException e) {e.printStackTrace(); }
                        }
                    });
            
                    
                    try {
                        consumer.subscribe(Arrays.asList(topic));
                        while (true) {
            
                            System.out.println("Polling");
            
                            ConsumerRecords<String, String> records =  consumer.poll(Duration.ofMillis(1000));
            
                            for (ConsumerRecord<String, String> record: records) {
                                Object result = obj.StrToJsonConvert(record.value());
                                System.out.println("Key: " + record.key());
                                // System.out.println("Value: " + record.value());
                                System.out.println("message: "+ result);
                                System.out.println("Partition: " + record.partition());
                                System.out.println("Offset: " + record.offset());
                            }
                    }
                    }  
                    catch (Exception e) { System.out.println("Unexpected exception in the consumer: "+ e);} 
                    finally {consumer.close(); System.out.println("The consumer is now gracefully shut down");}
                
                
                }



}



