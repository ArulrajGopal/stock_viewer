package pack1;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;


public class trigger_for_lambda implements RequestHandler<DynamodbEvent, Void> {

    @Override
    public Void handleRequest(DynamodbEvent event, Context context) {
        // Log the entire event object
        System.out.println(event);

        // Iterate over each record in the event
        for (DynamodbEvent.DynamodbStreamRecord record : event.getRecords()) {
            logDynamoDBRecord(record);
        }
        return null;
    }

    private void logDynamoDBRecord(DynamodbEvent.DynamodbStreamRecord record) {
        System.out.println(record.getEventID());
        System.out.println(record.getEventName());
        System.out.println(record.getDynamodb());
    }

  
    }
