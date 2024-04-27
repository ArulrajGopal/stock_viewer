package group1;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

public class dynamodb_get_data {

    public static void main (String[] args){

        String key = "Arulraj";

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDb = new DynamoDB(client);

        Table table = dynamoDb.getTable("table-1");
		GetItemSpec spec = new GetItemSpec().withPrimaryKey("primary_id", key);
        Item outcome = table.getItem(spec);

        System.out.println(outcome.get("primary_id"));
        System.out.println(outcome.get("description"));
    }


}
