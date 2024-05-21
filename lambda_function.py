import json
import boto3

topic_arn = "arn:aws:sns:us-east-1:042488648100:MyTopic1"

def send_sns(message, subject):
    try:
        client = boto3.client("sns")
        result = client.publish(TopicArn=topic_arn, Message=message, Subject=subject)
        if result['ResponseMetadata']['HTTPStatusCode'] == 200:
            print(result)
            print("Notification send successfully..!!!")
            return True
    except Exception as e:
        print("Error occured while publish notifications and error is : ", e)
        return True

def lambda_handler(event, context):
    try:
        if event ["Records"][0]["eventName"]=="INSERT":
            output = event["Records"][0]["dynamodb"]["NewImage"]
            message = str(output)
            subject = "FROM AWS"
            SNSResult = send_sns(message, subject)
            if SNSResult :
                print("Notification Sent..") 
                return SNSResult
            else:
                return False
    except :
        print ("Error occured while processing and error is : ",e)
        


    
