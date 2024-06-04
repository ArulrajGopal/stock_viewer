package ddl;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.CreateEventSourceMappingRequest;
import software.amazon.awssdk.services.lambda.model.CreateFunctionRequest;
import software.amazon.awssdk.services.lambda.model.FunctionCode;
import software.amazon.awssdk.services.lambda.model.Runtime;
import software.amazon.awssdk.services.lambda.model.ResourceConflictException;

import java.io.*;
import java.util.zip.*;

import org.json.JSONArray;

public class create_lambda_with_dynamo_trigger {
        public static void main(String[] args) throws Exception {
                try {
                        access_config_for_ddl access_con_obj = new access_config_for_ddl();
                        JSONArray lambda_func_list = access_con_obj.get_lambda_functions();
                        String role = access_con_obj.get_iam_role();
                        Region region = Region.US_EAST_1;

                        LambdaClient lambdaClient = LambdaClient.builder()
                                                                .region(region)
                                                                .build();

                        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                                                                .region(region)
                                                                .build();

                        for (Object element : lambda_func_list) {

                                String func_name = (String) element;

                                String handler = func_name+".lambda_handler";
                                String filename = func_name+".py";
                                String table_name = func_name.split("_")[0]+"_tbl";

                                
                                // read python file into bytes
                                byte[] lambdaFunctionBytes = readPythonToByte(filename);
                
                                // Get the Stream ARN for the table
                                String streamArn = getStreamArn(dynamoDbClient, table_name);
                
                                // Create the Lambda function
                                createLambdaFunction(lambdaClient, func_name, lambdaFunctionBytes, role, handler);
                
                                // Create the event source mapping
                                createEventSourceMapping(lambdaClient, func_name, streamArn);
                
  

                        }

                        lambdaClient.close();
                        dynamoDbClient.close();

                }
                catch (Exception e) {
                        System.out.println("Failed to create sns topic or subscribe: ");
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
        
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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