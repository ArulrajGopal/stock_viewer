package ddl;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.common.errors.TopicExistsException;
import org.json.JSONArray;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.SnsException;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;


public class create_sns_topic_with_subscription {
    public static void main(String[] args) {

        try {
            access_config_for_ddl access_con_obj = new access_config_for_ddl();
            JSONArray topics_list = access_con_obj.get_sns_topic();
            System.out.println(topics_list);


            // SnsClient snsClient = SnsClient.builder().region(Region.US_EAST_1).build();

            // String sector = "BANK";
            // String email = "arulrajgopal@outlook.com";

            // createSnsTopic (sector);
            // subEmail(snsClient, sector, email);
            // snsClient.close();
        

        }
        catch (Exception e) {
            System.out.println("Failed to create sns topic or topic already exists");
            }
        }





	
// 	public static void createSnsTopic(String sector) {
        

// 		try {
//             CreateTopicRequest request = CreateTopicRequest.builder()
//                     .name(topicName)
//                     .build();

//             CreateTopicResponse result = snsClient.createTopic(request);
//             System.out.println(result.topicArn());


//         } catch (SnsException e) {
//             System.err.println(e.awsErrorDetails().errorMessage());
//             System.exit(1);
//         }
// }


//     public static void subEmail(SnsClient snsClient, String topicArn, String email) {
//         try {

//             String topicArn = "arn:aws:sns:us-east-1:042488648100:MyTopic3";


//             SubscribeRequest request = SubscribeRequest.builder()
//                     .protocol("email")
//                     .endpoint(email)
//                     .returnSubscriptionArn(true)
//                     .topicArn(topicArn)
//                     .build();

//             SubscribeResponse result = snsClient.subscribe(request);
//             System.out.println("Subscription ARN: " + result.subscriptionArn() + "\n\n Status is "
//                     + result.sdkHttpResponse().statusCode());

//         } catch (SnsException e) {
//             System.err.println(e.awsErrorDetails().errorMessage());
//             System.exit(1);
//         }
// }   


}


