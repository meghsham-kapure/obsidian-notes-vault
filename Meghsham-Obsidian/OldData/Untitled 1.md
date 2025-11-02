# Kafka and zookeepr setup
```


sudo docker exec -it new-kafka kafka-topics.sh --bootstrap-server localhost:9092 --topic dummy3 --create --partitions 1 --replication-factor 1


sudo docker stop new-kafka new-zookeeper
sudo docker rm new-kafka new-zookeeper
sudo rm -rf /var/lib/zookeeper
sudo rm -rf /var/lib/kafka-logs

sudo docker run -d --name new-zookeeper --network my-network-pinot -p 2181:2181 zookeeper:3.7

sudo docker ps | grep new-zookeeper


sudo docker run -d --name new-kafka \
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




sudo docker ps | grep new-kafka

# start all container
docker restart 999c86292700 e180cfc0bda6 80f21172a6f0 65a369eb21d5 11fee5eb6340

# List all Kafka topics
docker exec -it new-kafka kafka-topics.sh --bootstrap-server localhost:9092 --list


# Create a Kafka topic named <GIVE_TOPIC_NAME>
docker exec -it new-kafka kafka-topics.sh --create --topic <GIVE_TOPIC_NAME> --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1


# Send messages to a Kafka topic <GIVE_TOPIC_NAME>
docker exec -it new-kafka kafka-console-producer.sh --broker-list localhost:9092 --topic <GIVE_TOPIC_NAME>


# Consume messages from Kafka topic <GIVE_TOPIC_NAME>
docker exec -it new-kafka kafka-console-consumer.sh --topic <GIVE_TOPIC_NAME> --from-beginning --bootstrap-server localhost:9092

# Create a Kafka topic named <GIVE_TOPIC_NAME>
docker exec -it new-kafka kafka-topics.sh --create --topic transaction-topic-new --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1


# Send messages to a Kafka topic <GIVE_TOPIC_NAME>
docker exec -it new-kafka kafka-console-producer.sh --broker-list localhost:9092 --topic transaction-topic-new


# Consume messages from Kafka topic <GIVE_TOPIC_NAME>
docker exec -it new-kafka kafka-console-consumer.sh --topic transaction-topic-new --from-beginning --bootstrap-server localhost:9092



```

# pinot_with_docker
```

  
docker run --network host -p 8099:8099 -v /home/meghsham.kapure/Documents/SSP/new:/pinot-files \
-e BASE_DIR_PINOT=/pinot-files -e PINOT_ZK_ADDRESS=localhost:2181 -e KAFKA_BROKER=localhost:9092 \
--name pinot-broker apachepinot/pinot:latest StartBroker




docker run \
  --network host \
  -p 8098:8098 \
  -v /home/meghsham.kapure/Documents/SSP/new:/pinot-files \
  -e BASE_DIR_PINOT=/pinot-files \
  -e PINOT_ZK_ADDRESS=localhost:2181 \
  -e KAFKA_BROKER=localhost:9092 \
  --name pinot-server \
  apachepinot/pinot:latest \
  StartServer
  


  
docker run --network host -p 9001:9000 -v /home/meghsham.kapure/Documents/SSP/new:/pinot-files \
-e BASE_DIR_PINOT=/pinot-files -e PINOT_ZK_ADDRESS=localhost:2181 -e KAFKA_BROKER=localhost:9092 \
--name pinot-controller apachepinot/pinot:latest StartController


docker run --network host -p 8098:8098 -v /home/meghsham.kapure/Documents/SSP/new:/pinot-files \
-e BASE_DIR_PINOT=/pinot-files -e PINOT_ZK_ADDRESS=localhost:2181 -e KAFKA_BROKER=localhost:9092 \
--name pinot-server apachepinot/pinot:latest StartServer



export BASE_DIR_PINOT=/home/meghsham.kapure/Documents/SSP/new

         
docker exec -it pinot-controller /bin/bash -c "bin/pinot-admin.sh AddTable -tableConfigFile \$BASE_DIR_PINOT/tableConfigRealTime.json -schemaFile \$BASE_DIR_PINOT/SchemaRealTime.json -controllerPort 9000 -exec"


#Copy the Schema File (SchemaRealTime.json) into the container:
docker cp /home/meghsham.kapure/Documents/SSP/copy_file/SchemaRealTime.json pinot-controller:/tmp/SchemaRealTime.json

#Copy the Table Config File (tableConfigRealTime.json) into the container:
docker cp /home/meghsham.kapure/Documents/SSP/copy_file/tableConfigRealTime.json pinot-controller:/tmp/tableConfigRealTime.json

#Copy the CSV File (transactions_new_min.csv) into the container:
docker cp /home/meghsham.kapure/Documents/SSP/copy_file/transactions_new_min.csv pinot-controller:/tmp/transactions_new_min.csv




```

# table Config Real Time
```
{
  "tableName": "transactionTable",
  "tableType": "REALTIME",
  "segmentsConfig": {
    "schemaName": "transactionSchma",
    "replication": "1",
    "timeColumnName": "timestamp",
    "timeType": "MILLISECONDS",
    "segmentAssignmentStrategy": "BalanceNumSegmentAssignmentStrategy",
    "segmentPartitionConfig": {
      "columnPartitionMap": {
        "MerchantId": {
          "functionName": "Murmur",
          "numPartitions": 10
        }
      }
    }
  },
  "tableIndexConfig": {
    "invertedIndexColumns": ["MerchantId", "Status", "PaymentMethod"],
    "rangeIndexColumns": ["TxnAmount"],
    "starTreeIndexConfigs": [
      {
        "dimensionsSplitOrder": [
          "MerchantId",
          "PaymentMethod",
          "Status"
        ],
        "functionColumnPairs": [
          "SUM__TxnAmount",
          "COUNT__TransactionId"
        ],
        "maxLeafRecords": 10000
      }
    ],
    "loadMode": "MMAP",
    "streamConfigs": {
    "streamType": "kafka",
    "stream.kafka.topic.name": "transaction-topic",
    "stream.kafka.broker.list": "kafka_broker:9092",
    "stream.kafka.consumer.type": "lowlevel",
    "stream.kafka.consumer.prop.auto.offset.reset": "smallest",
    "stream.kafka.decoder.class.name": "org.apache.pinot.plugin.stream.kafka.KafkaJSONMessageDecoder",
    "stream.kafka.consumer.prop.group.id": "pinot_consumer_group_transactionRecordsOpRealTime1",
    "stream.kafka.consumer.property.request.timeout.ms": "60000",
    "stream.kafka.consumer.property.session.timeout.ms": "50000",
    "realtime.segment.flush.threshold.rows": "5000000",
    "realtime.segment.flush.threshold.time": "1m"
}
  },
  "fieldConfigList": [
    {
      "name": "MerchantId",
      "encodingType": "DICTIONARY"
    },
    {
      "name": "Status",
      "encodingType": "DICTIONARY"
    },
    {
      "name": "PaymentMethod",
      "encodingType": "DICTIONARY"
    }
  ],
  "tenants": {
    "broker": "DefaultTenant",
    "server": "DefaultTenant"
  },
  "metadata": {}
}

```

#
```

{
  "schemaName": "transactionSchma",
  "dimensionFieldSpecs": [
    {"name": "TransactionId", "dataType": "STRING"},
    {"name": "MerchantId", "dataType": "STRING"},
    {"name": "MerchantName", "dataType": "STRING"},
    {"name": "PaymentMethod", "dataType": "STRING"},
    {"name": "TransactionDate", "dataType": "STRING"},
    {"name": "Status", "dataType": "STRING"},
    {"name": "AuthStatus", "dataType": "STRING"},
    {"name": "BdOrderId", "dataType": "STRING"},
    {"name": "AuthenticationType", "dataType": "STRING"},
    {"name": "ThreeDsParameter", "dataType": "STRING"},
    {"name": "BankId", "dataType": "STRING"},
    {"name": "ItemCode", "dataType": "STRING"},
    {"name": "PaymentMethodType", "dataType": "STRING"},
    {"name": "TxnProcessType", "dataType": "STRING"},
    {"name": "IssuerResponseType", "dataType": "STRING"},
    {"name": "JweCard", "dataType": "STRING"},
    {"name": "AdditionalInfo1", "dataType": "STRING"},
    {"name": "AdditionalInfo2", "dataType": "STRING"},
    {"name": "DeviceType", "dataType": "STRING"}
  ],
  "metricFieldSpecs": [
    {"name": "TxnAmount", "dataType": "DOUBLE"}
  ],
  "dateTimeFieldSpecs": [
    {
      "name": "timestamp",
      "dataType": "LONG",
      "format": "1:MILLISECONDS:EPOCH",
      "granularity": "1:MILLISECONDS"
    }
  ]
}

```