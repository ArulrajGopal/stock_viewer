Steps to start the execution
============================
*    spin up linux machine
*    install java, Kafka(configure bashrc), install awscli(configure accesskeys)

                  PATH="$PATH:~/kafka_2.13-3.1.0/bin"
*    start zookeeper and kafka
  
                  zookeeper-server-start.sh ~/kafka_2.13-3.1.0/config/zookeeper.properties
                  kafka-server-start.sh ~/kafka_2.13-3.1.0/config/server.properties
     
*   create IAM role for lambda and configure in config.json
     
*   execute ddl file which creates the below

        1. kafka topics
    
        2. lambda with dynamodb trigger
    
        3. tables in dynamodb
    
        4. SNS topic and subscription
          
*   execute main java file





