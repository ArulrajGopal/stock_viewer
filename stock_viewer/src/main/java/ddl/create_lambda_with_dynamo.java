package ddl;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;
import software.amazon.awssdk.services.dynamodb.model.StreamSpecification;
import software.amazon.awssdk.services.dynamodb.model.UpdateTableRequest;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.CreateEventSourceMappingRequest;
import software.amazon.awssdk.services.lambda.model.CreateFunctionRequest;
import software.amazon.awssdk.services.lambda.model.FunctionCode;
import software.amazon.awssdk.services.lambda.model.Runtime;
import software.amazon.awssdk.services.lambda.model.ResourceConflictException;
import software.amazon.awssdk.services.lambda.model.InvalidParameterValueException;


import java.io.*;
import java.util.zip.*;

import org.json.JSONArray;

public class create_lambda_with_dynamo {
        public static void main(String[] args) throws Exception {
                try {
                        access_config_for_ddl access_con_obj = new access_config_for_ddl();
                        JSONArray sector_list = access_con_obj.get_sectors_list();
                        String role = access_con_obj.get_iam_role();
                        Region region = Region.US_EAST_1;
                        
                        String partition_key = "primary_id";

                        LambdaClient lambdaClient = LambdaClient.builder().region(region).build();
                        DynamoDbClient dynamoDbClient = DynamoDbClient.builder().region(region).build();
                                                                
                                                                

                        for (Object element : sector_list) {

                                String sector = (String) element;

                                String func_name = sector+"_lam";
                                String handler = func_name+".lambda_handler";
                                String filename = func_name+".py";
                                String table_name = sector+"_tbl";


                                createTable(dynamoDbClient, table_name, partition_key);
                                byte[] lambdaFunctionBytes = readPythonToByte(filename);
                                enableDynamoDBStream(dynamoDbClient, table_name);
                                String streamArn = getStreamArn(dynamoDbClient, table_name);
                                createLambdaFunction(lambdaClient, func_name, lambdaFunctionBytes, role, handler);
                                createEventSourceMapping(lambdaClient, func_name, streamArn);


                        }

                        lambdaClient.close();
                        dynamoDbClient.close();

                }
                catch (Exception e) {
                        System.out.println("Failed to lambda function or failed at mapping : "+e);
                }


        }


        public static void createTable(DynamoDbClient ddb, String tableName, String partition_key) {
                try {
                    CreateTableRequest request = CreateTableRequest.builder()
                            .attributeDefinitions(
                                    AttributeDefinition.builder()
                                            .attributeName(partition_key)
                                            .attributeType(ScalarAttributeType.N)
                                            .build())
                            .keySchema(
                                    KeySchemaElement.builder()
                                            .attributeName(partition_key)
                                            .keyType(KeyType.HASH) // Partition key
                                            .build())
                            .provisionedThroughput(
                                    ProvisionedThroughput.builder()
                                            .readCapacityUnits(5L)
                                            .writeCapacityUnits(5L)
                                            .build())
                            .tableName(tableName)
                            .build();
        
                    ddb.createTable(request);
                    System.out.println("Table created successfully: " + tableName);
        
                } catch (DynamoDbException e) {
                    System.out.println(e.getMessage());
                }
            }
    



    private static byte[] readPythonToByte(String filename) {

        try {

        String pythonFilePath = "stock_viewer/src/main/python/lambda_function.py";

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
        ZipEntry zipEntry = new ZipEntry(filename);
        zipOut.putNextEntry(zipEntry);

        // Write the Python file bytes to the ZIP entry
        zipOut.write(pythonFileBytes, 0, pythonFileBytes.length);

        // Close the ZIP entry and the ZIP output stream
        zipOut.closeEntry();
        zipOut.close();

        // Get the resulting ZIP file bytes
        byte[] zipFileBytes = byteArrayOutputStream.toByteArray();

        return zipFileBytes;
        
        } 
        catch (Exception e) {
                System.out.println("convert python file into byte : "+e);
                return null;
        }
    }



    private static String getStreamArn(DynamoDbClient dynamoDbClient, String tableName) {

        try {
                return dynamoDbClient.describeTable(r -> r.tableName(tableName)).table().latestStreamArn();
        }
        catch (Exception e) {
                System.out.println("failed at getting stream arn : "+e);
                return null;
        }
        
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
            } 
        catch (ResourceConflictException e) {
                System.out.println("lambda already exists");

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
                System.out.println("event source already mapped" );

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

}