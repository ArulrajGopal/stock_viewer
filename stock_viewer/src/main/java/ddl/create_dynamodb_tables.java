package ddl;

import java.io.IOException;

import org.json.JSONArray;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;


public class create_dynamodb_tables {
    public static void main(String[] args) throws IOException {
        // Define the region you are working in
        Region region = Region.US_EAST_1;
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        access_config_for_ddl access_con_obj = new access_config_for_ddl();
        JSONArray tables_list = access_con_obj.get_tables_list();
        String partition_key = "primary_id";

        for (Object tbl : tables_list) {

            String tbl_name = (String) tbl;
            createTable(ddb, tbl_name, partition_key);

        }

        ddb.close();
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
}