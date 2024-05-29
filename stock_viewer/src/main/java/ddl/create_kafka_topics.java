package ddl;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.TopicExistsException;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class create_kafka_topics {
    public static void main(String[] args) {

         String bootstrapServers = "localhost:9092";
         int numPartitions = 3;
         short replicationFactor = 1;

        // Create the AdminClient configuration
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);


         String topicName = "bank_tp";
 
         // Create the AdminClient
         try (AdminClient adminClient = AdminClient.create(config)) {
             // Define the new topic
             NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
 
             // Create the topic
             adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
 
             System.out.println("Topic created successfully");
         } 


         catch (InterruptedException | ExecutionException | TopicExistsException e) {
            System.out.println("Failed to create topic or topic already exists ");

        }
     }

    }
    