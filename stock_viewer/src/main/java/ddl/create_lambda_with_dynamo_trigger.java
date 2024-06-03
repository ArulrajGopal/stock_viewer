package group1;

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
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import java.io.*;
import java.util.zip.*;

public class create_lambda_with_dynamo_trigger {
        public static void main(String[] args) throws Exception {
                String functionName = "lambda_function";
                String handler = "lambda_function.lambda_handler";
                String role = "arn:aws:iam::042488648100:role/arulraj_lambda_role";
                String tableName = "test_tbl";

                Region region = Region.US_EAST_1;
                LambdaClient lambdaClient = LambdaClient.builder()
                        .region(region)
                        .build();

                DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                        .region(region)
                        .build();


                // read python file into bytes
                byte[] lambdaFunctionBytes = readPythonToByte();

                // Enable DynamoDB Streams on the table
                enableDynamoDBStream(dynamoDbClient, tableName);

                // Get the Stream ARN for the table
                String streamArn = getStreamArn(dynamoDbClient, tableName);

                // Create the Lambda function
                createLambdaFunction(lambdaClient, functionName, lambdaFunctionBytes, role, handler);

                // Create the event source mapping
                createEventSourceMapping(lambdaClient, functionName, streamArn);

                lambdaClient.close();
                dynamoDbClient.close();
        }
    



    private static byte[] readPythonToByte() {

        try {

        String pythonFilePath = "src\\main\\python\\group2\\lambda_function.py";

        // Read the Python file into a byte array
        FileInputStream fis = new FileInputStream(pythonFilePath);
        ByteArrayOutputStream pythonFileOutput = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
                pythonFileOutput.write(buffer, 0, length);
        }
        fis.close();
        byte[] pythonFileBytes = pythonFileOutput.toByteArray();

        // Create a byte array output stream to hold the ZIP file content
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream);

        // Create a new ZIP entry for the Python file
        ZipEntry zipEntry = new ZipEntry("lambda_function.py");
        zipOut.putNextEntry(zipEntry);

        // Write the Python file bytes to the ZIP entry
        zipOut.write(pythonFileBytes, 0, pythonFileBytes.length);

        // Close the ZIP entry and the ZIP output stream
        zipOut.closeEntry();
        zipOut.close();

        // Get the resulting ZIP file bytes
        byte[] zipFileBytes = byteArrayOutputStream.toByteArray();

        return zipFileBytes;
        
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }




    private static void enableDynamoDBStream(DynamoDbClient dynamoDbClient, String tableName) {

        try {
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
        catch (DynamoDbException e){
                System.out.println("Table already has an enabled stream");
        }


    }




    private static String getStreamArn(DynamoDbClient dynamoDbClient, String tableName) {
        return dynamoDbClient.describeTable(r -> r.tableName(tableName)).table().latestStreamArn();
    }




    private static void createLambdaFunction(LambdaClient lambdaClient,
                                             String functionName,
                                             byte[] lambdaFunctionBytes,
                                             String role,
                                             String handler) throws Exception {

        try {
                SdkBytes fileToUpload = SdkBytes.fromByteArray(lambdaFunctionBytes);
    
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
            } catch (ResourceConflictException e) {
                System.out.println("Lambda function already exists");
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