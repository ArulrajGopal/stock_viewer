Steps to start the execution
============================
*    spin up linux machine
*    install java, Kafka(configure bashrc), install awscli(configure accesskeys)

                  PATH="$PATH:~/kafka_2.13-3.1.0/bin"
*    start zookeeper and kafka
  
                  zookeeper-server-start.sh ~/kafka_2.13-3.1.0/config/zookeeper.properties
                  kafka-server-start.sh ~/kafka_2.13-3.1.0/config/server.properties
     
*   create IAM role for lambda and configure in config.json
     
*   execute below files in the same order which is data definintion language.
    In order to run the below files, arugments already configured in config.json file.
    In case of arguments change, change only in config.json file

        1. creat kafka topics 

        2. create dynamodb tables 
    
        3. create sns topic 

        4. create subscription 
    
        5. create lambda and evert source mapping with dyanmodb 
          
*   execute main java file





