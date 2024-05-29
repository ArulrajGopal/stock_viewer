package ddl;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.TopicExistsException;
import org.json.JSONArray;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class create_kafka_topics {
    public static void main(String[] args) throws IOException {

        String bootstrapServers = "localhost:9092";
        int num_partitions = 3;
        short rep_factor = 1;

        // Create the AdminClient configuration
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        access_config_for_ddl access_con_obj = new access_config_for_ddl();
        JSONArray topics_list = access_con_obj.get_topics_list();

        for (Object topic : topics_list) {

            String topic_name = (String) topic;
  
            // Create the AdminClient
            try (AdminClient adminClient = AdminClient.create(config)) {
            // Define the new topic
            NewTopic newTopic = new NewTopic(topic_name, num_partitions, rep_factor);

            // Create the topic
            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();

            System.out.println("Topic created successfully");
            
            } 

            catch (InterruptedException | ExecutionException | TopicExistsException e) {
                        System.out.println(topic_name+" - Failed to create topic or topic already exists");

            }
        }

    }

}
    