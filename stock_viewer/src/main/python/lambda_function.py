import json
import boto3

def lambda_handler(event, context):
    try:
        if event ["Records"][0]["eventName"]=="INSERT":

            #extracting message from the event
            new_image = event["Records"][0]["dynamodb"]["NewImage"]
            message = "As of "+str(new_image["lastUpdateTime"])+" , the "+ new_image["symbol"].lower() +" stock was trading at "+ str(new_image["lastPrice"]) + " INR"

            # developing topic_arn with event
            eventSourceARN = event["Records"][0]["eventSourceARN"]
            aws_account_id = eventSourceARN.split(':')[4]
            sector = eventSourceARN.split(':')[5].split("/")[1].split("_")[0]
            topic_name = sector+"_snstp"
            region = "us-east-1"
            sns_topic_arn = "arn:aws:sns:"+region+":"+aws_account_id+":"+topic_name

            # developing subject
            subject = "update at "+sector
            
            client = boto3.client("sns")
            result = client.publish(TopicArn=sns_topic_arn, Message=message, Subject=subject)
            print(message)
            print("Notification send successfully..!!!!!")

    except Exception as e:
        print ("Error occured while processing and error is : ",e)
        


    
