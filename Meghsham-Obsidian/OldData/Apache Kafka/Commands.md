1. Pull and Running zookeeper # Run in new terminal window
```shell

docker run -p 2181:2181 zookeeper:latest

```

----
2. Pull and running Kafka # Run in new terminal window
```shell

docker run -p 9092:9092 \
-e KAFKA_ZOOKEEPER_CONNEC=192.168.43.150:2181 \
-e KAFKA_ADVERTISED_LISTENER=PLAINTEXT://192.168.43.150:9092 \
-e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
confluentinc/cp-kafka

```

----
, 

wsl --stattus

default version : 2



wsl --update
wsl --set-default-version 2

