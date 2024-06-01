package ddl;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;

public class create_sns_topic {
    public static void main(String[] args) {
        SnsClient snsClient = SnsClient.builder().build();
        CreateTopicRequest request = CreateTopicRequest.builder()
                .name("MyTopic1")
                .build();

        CreateTopicResponse result = snsClient.createTopic(request);

        // Print out the topic ARN
        String topicArn = result.topicArn();
        System.out.println("Created topic ARN: " + topicArn);

        snsClient.close();
    }
}
