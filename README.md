# spring-kafka-streaming
Demo project using a spring boot kafka consumer for stream processing

## Pre-requisites
1. Install jdk 8
2. Download and unzip Zookeeper 3.4.10. The default Zookeeper package comes with a sample cfg file in the conf folder. Rename the file to zoo.cfg and run bin/zkServer.sh start. This should start zookeeper on the default port 2181. Kafka requires zookeeper to store configuration.
3. Download and unzip Kafka 2.0 and run bin/kafka-server-start.sh config/server.properties. The server.properties has the port for zookeeper and you need to re-confirm that it points to the zookeeper port.
4. Create 2 topics in Kafka using the following commands after _**cd**_ to the kafka install directory
```
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic raw
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic valid
```

## Running the code
1. Clone this repo and _**cd**_ to the cloned folder
2. Run mvn spring-boot:run
3. Add messages to the raw topic by issuing the following commands after _**cd**_ to the kafka install directory
```
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic raw
Add the following json as input {"name": "user1", "address" : {"line1":"xzy", "line2":"jenga", "pin":"560068"}}
```
4. Use a console consumer on the valid topic to see the messages that have been processed by the validator streaming consumer.
```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic valid --from-beginning
```

## Understanding the source code
**KafkaConfig** has the boilerplate code to initialise a kafka producer and consumer 
**BaseStreamProcessor** has a helper method to push messages to a Kafka topic
**Validator** is the stream processor and uses annotations to listen to the input topic. It gets a map as input as a JsonSer/Deser has been configured in the KafkaConfig.
**application.properties** has the input and output topic names that can be configured. It defaults to raw (input topic) and valid (output topic)

## Pausing and resuming the validator 
You may have some scenarios where you want to pause the kafka consumer for some duration. This can be done using REST endpoints exposed by the **StreamController**.

You can look at the offsets of the topic, the validator consumer's offset and lag by executing the following command
```
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group validator
```
