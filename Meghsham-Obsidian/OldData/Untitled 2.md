# Apache Pinot Docker Setup and Data Ingestion Guide

## 1. Setup Network and Dependencies

First, ensure you have Docker installed and create a network for the containers:

```bash
# Create a Docker network if it doesn't exist
docker network create my-network-pinot
```

## 2. Setup Zookeeper and Kafka

### Start Zookeeper:
```bash
# Start Zookeeper container
 docker run -d --name new-zookeeper \
  --network my-network-pinot \
  -p 2181:2181 \
  zookeeper:3.7

# Verify Zookeeper is running
 docker ps | grep new-zookeeper
```

### Start Kafka:
```bash
# Start Kafka container
 docker run -d --name new-kafka \
  --network my-network-pinot \
  -p 9092:9092 \
  -e KAFKA_ZOOKEEPER_CONNECT=new-zookeeper:2181 \
  -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
  -e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=PLAINTEXT:PLAINTEXT \
  -e KAFKA_INTER_BROKER_LISTENER_NAME=PLAINTEXT \
  -e KAFKA_LOG_RETENTION_HOURS=24 \
  -e KAFKA_LOG_SEGMENT_BYTES=1073741824 \
  -e KAFKA_DELETE_TOPIC_ENABLE=true \
  --restart unless-stopped \
  wurstmeister/kafka:2.13-2.7.1

# Verify Kafka is running
 docker ps | grep new-kafka
```

### Create Kafka Topic:
```bash
# Create a topic for transactions
 docker exec -it new-kafka kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --topic transaction-topic \
  --create \
  --partitions 1 \
  --replication-factor 1
```

## 3. Start Apache Pinot Components

### Start Pinot Controller:
```bash
docker run --network host \
  -p 9001:9000 \
  -v /home/meghsham.kapure/Documents/SSP/copy_file:/pinot-files \
  -e BASE_DIR_PINOT=/pinot-files \
  -e PINOT_ZK_ADDRESS=localhost:2181 \
  -e KAFKA_BROKER=localhost:9092 \
  --name pinot-controller \
  apachepinot/pinot:latest \
  StartController
```

### Start Pinot Broker:
```bash
docker run --network host \
  -p 8099:8099 \
  -v /home/meghsham.kapure/Documents/SSP/copy_file:/pinot-files \
  -e BASE_DIR_PINOT=/pinot-files \
  -e PINOT_ZK_ADDRESS=localhost:2181 \
  -e KAFKA_BROKER=localhost:9092 \
  --name pinot-broker \
  apachepinot/pinot:latest \
  StartBroker
```

### Start Pinot Server:
```bash
docker run --network host \
  -p 8098:8098 \
  -v /home/meghsham.kapure/Documents/SSP/copy_file:/pinot-files \
  -e BASE_DIR_PINOT=/pinot-files \
  -e PINOT_ZK_ADDRESS=localhost:2181 \
  -e KAFKA_BROKER=localhost:9092 \
  --name pinot-server \
  apachepinot/pinot:latest \
  StartServer
```

## 4. Setup Schema and Table Configuration

### Copy Configuration Files:
```bash
# Copy schema file
docker cp /home/meghsham.kapure/Documents/SSP/copy_file/SchemaRealTime.json pinot-controller:/tmp/SchemaRealTime.json

# Copy table config file
docker cp /home/meghsham.kapure/Documents/SSP/copy_file/tableConfigRealTime.json pinot-controller:/tmp/tableConfigRealTime.json

# Copy data file (if needed)
docker cp /home/meghsham.kapure/Documents/SSP/copy_file/transactions_new_min.csv pinot-controller:/tmp/transactions_new_min.csv
```

### Create Table:
```bash
# Add table with schema and configuration
docker exec -it pinot-controller /bin/bash -c \
  "bin/pinot-admin.sh AddTable \
  -tableConfigFile /tmp/tableConfigRealTime.json \
  -schemaFile /tmp/SchemaRealTime.json \
  -controllerPort 9000 -exec"
```

## 5. Verify Setup

```bash
# Check Kafka topics
docker exec -it new-kafka kafka-topics.sh --bootstrap-server localhost:9092 --list

# You can access the Pinot UI at:
# http://localhost:9000
```

## 6. Cleanup (if needed)

```bash
# Stop containers
 docker stop new-kafka new-zookeeper pinot-controller pinot-broker pinot-server

# Remove containers
 docker rm new-kafka new-zookeeper pinot-controller pinot-broker pinot-server

# Clean up data directories
 rm -rf /var/lib/zookeeper
 rm -rf /var/lib/kafka-logs
```

Note: Replace `/path/to/your/config` with your actual configuration directory path in all commands.







# Delete existing topic in Kafka
```shell
docker exec -it new-kafka kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic <TOPIC-NAME>

```




----


### **1. Check If Messages Exist in the Topic**

Run this command to check if messages are being produced in the topic:

```bash
docker exec -it new-kafka kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic transactionDemo1 --from-beginning
```

- If the topic has messages, they will be displayed in real time.
- If no output appears, the topic might be empty.

To **exit** the consumer, press **CTRL+C**.

---

### **2. Count the Number of Messages in the Topic**

If you want to check how many messages exist in `transactionDemo1` before consuming:

```bash
docker exec -it new-kafka kafka-run-class.sh kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic transactionDemo1 --time -1
```

This will output something like:

```
transactionDemo1:0:150
```

The last number (`150`) indicates the total number of messages.

---

### **3. Produce a Test Message to the Topic**

If you want to manually send a message to the topic for testing:

```bash
docker exec -it new-kafka kafka-console-producer.sh --broker-list localhost:9092 --topic transactionDemo1
```

Type a message like:

```
{"TransactionId":"TXN999","MerchantId":"M999","MerchantName":"Test Store","Status":"Success","PaymentMethod":"CreditCard","AuthStatus":"Approved","TransactionDate":"2025-02-14","TxnAmount":100.00}
```

Press **Enter** after typing each message. Press **CTRL+C** to exit.

---

### **4. Consume the Messages Again**

Run the consumer command again:

```bash
docker exec -it new-kafka kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic transactionDemo1 --from-beginning
```

This will show all messages stored in the topic.

---

### **5. Delete the Topic After Verification**

Once you confirm the messages, you can proceed with deleting the topic:

```bash
docker exec -it new-kafka kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic transactionDemo1
```

---
