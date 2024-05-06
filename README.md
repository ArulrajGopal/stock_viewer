Steps to start the execution
============================
*    spin up linux machine
*    install java, Kafka(configure bashrc), install awscli(configure accesskeys)

                  PATH="$PATH:~/kafka_2.13-3.1.0/bin"
*    start zookeeper and kafka
  
                  zookeeper-server-start.sh ~/kafka_2.13-3.1.0/config/zookeeper.properties
                  kafka-server-start.sh ~/kafka_2.13-3.1.0/config/server.properties
*    create topics in kafka

                  kafka-topics.sh --bootstrap-server localhost:9092 --topic pharma --create --partitions 3 --replication-factor 1
                  kafka-topics.sh --bootstrap-server localhost:9092 --topic finservice --create --partitions 3 --replication-factor 1
                  kafka-topics.sh --bootstrap-server localhost:9092 --topic energy --create --partitions 3 --replication-factor 1
                  kafka-topics.sh --bootstrap-server localhost:9092 --topic fmcg --create --partitions 3 --replication-factor 1
     
*    check topic list
  
                  kafka-topics.sh --bootstrap-server localhost:9092 --list

*    if required, describe topics and delete topic
  
                  kafka-topics.sh --bootstrap-server localhost:9092 --topic topic_name --describe
                  kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic topic_name
     
*    create neccessary tables in dynamodb with correct primary key
*    now execute main_file.java





