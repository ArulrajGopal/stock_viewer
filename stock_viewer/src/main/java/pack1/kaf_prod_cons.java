package pack1;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.CooperativeStickyAssignor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONArray;

public class kaf_prod_cons {
    
    public void KafProducer (String sector){
        
                // create Producer Properties & Setting up properties
                Properties properties = new Properties();
                properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
                properties.setProperty("key.serializer", StringSerializer.class.getName());
                properties.setProperty("value.serializer", StringSerializer.class.getName());
        
                // create the Producer
                KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
                Random random = new Random();
                fetchstockdetails obj_fetchdetails = new fetchstockdetails();

                try {
                        while(true) {

                                System.out.println(sector+" Producing");

                                String key = String.valueOf(random.nextInt(3));
                                JSONArray fetched_data = obj_fetchdetails.fetchstockDetails_for_sector(sector);
                                String message = fetched_data.toString();
                                access_config obj = new access_config();
                                String topic = obj.get_topic_name(sector);


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
                        
                                try {Thread.sleep(20000);} 
                                catch (InterruptedException e) {e.printStackTrace();}

                        }
                }


                catch (Exception e) { 
                        System.out.println("Unexpected exception in the producer: "+ e);
                } 


                finally {
                        producer.flush();
                        producer.close();
                        System.out.println("The consumer is now gracefully shut down");
                }
        
        }


        public void KafConsumer(String sector) {

                String groupId = sector+"_group_id";
         
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
                access_config obj_Access_config = new access_config();
                utils obj_util = new utils();

                try {
                String topic = obj_Access_config.get_topic_name(sector);
                String table_name = obj_Access_config.get_table_name(sector);
                consumer.subscribe(Arrays.asList(topic));


                while (true) {
        
                        System.out.println(sector+"Polling");
        
                        ConsumerRecords<String, String> records =  consumer.poll(Duration.ofMillis(20000));
        
                        for (ConsumerRecord<String, String> record: records) {
                        
                        obj_util.load_into_dydb (table_name, record.value());
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
}
