Steps to start the execution
=======
*    spin up linux machine
*    install java, Kafka(configure bashrc), install awscli(configure accesskeys)
      PATH="$PATH:~/kafka_2.13-3.1.0/bin"
*    start zookeeper and kafka
      zookeeper-server-start.sh ~/kafka_2.13-3.1.0/config/zookeeper.properties
      kafka-server-start.sh ~/kafka_2.13-3.1.0/config/server.properties
*    create topics in kafka
      kafka-topics.sh --bootstrap-server localhost:9092 --topic demo_java --create --partitions 3 --replication-factor 1
      kafka-topics.sh --bootstrap-server localhost:9092 --list
      kafka-topics.sh --bootstrap-server localhost:9092 --topic demo_java --describe
      kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic demo_java
*    create neccessary tables in dynamodb with correct primary key
*    now execute master.java





