Steps to start the execution
============================
*    spin up linux machine
*    install java
  
            sudo apt-get install openjdk-11-jdk
            java --version

*   install kafka and configure bashrc

            PATH="$PATH:~/kafka_2.13-3.1.0/bin"

*   install awscli(configure accesskeys)

            curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
            unzip awscliv2.zip
            sudo ./aws/install

            aws --version

            aws configure
*   configure rapid api key - Take the "x-rapidapi-key" from rapid api and paste it in fetchstockdetails.java file
  
*    start zookeeper and kafka
  
            zookeeper-server-start.sh ~/kafka_2.13-3.1.0/config/zookeeper.properties
            kafka-server-start.sh ~/kafka_2.13-3.1.0/config/server.properties
     
*   create IAM role for lambda and configure in config.json
     
*   execute below files in the same order which is data definintion language.
    No need to change arugments, because already configured in config.json file.
    In case of arguments change, do change in config.json file

        1. create kafka topics 

        2. create lambda with dynamodb

        3. create sns topic and subscribe
          
*   execute main java file





