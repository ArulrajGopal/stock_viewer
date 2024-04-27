package group1;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.Item;



public class dynamodb_load_data {

    public static void main (String[] args){

        String key = "Shankar";
        String attribute = "fav_sport";
        String value = "football";


        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDb = new DynamoDB(client);

        Table table = dynamoDb.getTable("table-1");
        table.putItem(new Item().withPrimaryKey("primary_id", key).with(attribute, value));

        System.out.println("Success");

    }
}
