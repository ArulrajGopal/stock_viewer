package pack1;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.StreamSpecification;
import software.amazon.awssdk.services.dynamodb.model.UpdateTableRequest;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.CreateEventSourceMappingRequest;
import software.amazon.awssdk.services.lambda.model.CreateFunctionRequest;
import software.amazon.awssdk.services.lambda.model.FunctionCode;
import software.amazon.awssdk.services.lambda.model.Runtime;
import software.amazon.awssdk.services.lambda.model.ResourceConflictException;

import java.io.FileInputStream;
import java.io.InputStream;


public class CreateLambdaWithDynamoDBTrigger {
    public static void main(String[] args) throws Exception {
        String functionName = "lambda_function";
        String filePath = "stock_viewer/lambda_function.zip";
        String role = "arn:aws:iam::042488648100:role/arulraj_lambda_role";
        String handler = "lambda_function.lambda_handler";
        String tableName = "test1";
        String streamArn;

        Region region = Region.US_EAST_1;
        LambdaClient lambdaClient = LambdaClient.builder()
                .region(region)
                .build();

        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .region(region)
                .build();

        // Enable DynamoDB Streams on the table
        // enableDynamoDBStream(dynamoDbClient, tableName);

        // Get the Stream ARN for the table
        streamArn = getStreamArn(dynamoDbClient, tableName);

        // Create the Lambda function
        createLambdaFunction(lambdaClient, functionName, filePath, role, handler);

        // Create the event source mapping
        createEventSourceMapping(lambdaClient, functionName, streamArn);

        lambdaClient.close();
        dynamoDbClient.close();
    }



    private static void enableDynamoDBStream(DynamoDbClient dynamoDbClient, String tableName) {
        StreamSpecification streamSpecification = StreamSpecification.builder()
                .streamEnabled(true)
                .streamViewType("NEW_AND_OLD_IMAGES")
                .build();

        UpdateTableRequest updateTableRequest = UpdateTableRequest.builder()
                .tableName(tableName)
                .streamSpecification(streamSpecification)
                .build();

        dynamoDbClient.updateTable(updateTableRequest);
        System.out.println("DynamoDB Streams enabled on table " + tableName);
    }




    private static String getStreamArn(DynamoDbClient dynamoDbClient, String tableName) {
        return dynamoDbClient.describeTable(r -> r.tableName(tableName)).table().latestStreamArn();
    }




    private static void createLambdaFunction(LambdaClient lambdaClient,
                                             String functionName,
                                             String filePath,
                                             String role,
                                             String handler) throws Exception {
        try {
                InputStream is = new FileInputStream(filePath);
                SdkBytes fileToUpload = SdkBytes.fromInputStream(is);

                FunctionCode code = FunctionCode.builder()
                        .zipFile(fileToUpload)
                        .build();

                CreateFunctionRequest functionRequest = CreateFunctionRequest.builder()
                        .functionName(functionName)
                        .description("Created by the Lambda Java API")
                        .code(code)
                        .handler(handler)
                        .runtime(Runtime.PYTHON3_9)
                        .role(role)
                        .build();

                lambdaClient.createFunction(functionRequest);
                System.out.println("Lambda function created: " + functionName);
       }
       catch (ResourceConflictException e) {

        System.out.println("lambda function already exists");
        
        }

    }


    private static void createEventSourceMapping(LambdaClient lambdaClient, String functionName, String streamArn) {
        try {
                CreateEventSourceMappingRequest eventSourceMappingRequest = CreateEventSourceMappingRequest.builder()
                .eventSourceArn(streamArn)
                .functionName(functionName)
                .startingPosition("LATEST")
                .build();

                lambdaClient.createEventSourceMapping(eventSourceMappingRequest);
                System.out.println("Event source mapping created for function: " + functionName);
        }
        catch (ResourceConflictException e) {

                System.out.println("EventSourceMapping already exists");
                
        }

    }
}