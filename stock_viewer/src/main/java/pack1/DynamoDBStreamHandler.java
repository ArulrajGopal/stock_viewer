package pack1;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;

public class DynamoDBStreamHandler implements RequestHandler<DynamodbEvent, String> {

    @Override
    public String handleRequest(DynamodbEvent event, Context context) {
        // Log the entire event object
        System.out.println(event);

        // Iterate over each record in the event
        for (DynamodbEvent.DynamodbStreamRecord record : event.getRecords()) {
            System.out.println(record.getEventID());
            System.out.println(record.getEventName());
            System.out.println("DynamoDB Record: " + record.getDynamodb().toString());
        }
        return "Successfully processed " + event.getRecords().size() + " records.";
    }
}
