package ddl;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.SnsException;


public class create_sns_topic {
    public static void main(String[] args) {

        String topicName = "MyTopic3";
        SnsClient snsClient = SnsClient.builder().region(Region.US_EAST_1).build();

        try {
            CreateTopicRequest request = CreateTopicRequest.builder()
                    .name(topicName)
                    .build();

            CreateTopicResponse result = snsClient.createTopic(request);
            System.out.println(result.topicArn());


        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }

        
    }
}
