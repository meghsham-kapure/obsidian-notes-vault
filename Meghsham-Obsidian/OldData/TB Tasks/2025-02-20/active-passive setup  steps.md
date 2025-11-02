# Laptop 2 Setup (Secondary) - 192.168.90.76
# =========================================

# 1. Start ZooKeeper with explicit cluster ID
docker run -d --name new-zookeeper \
  --network host \
  -p 2181:2181 \
  -e ZOO_MY_ID=2 \
  -e ZOO_SERVERS="server.1=192.168.90.216:2888:3888;2181 server.2=192.168.90.76:2888:3888;2181" \
  -e ZOO_TICK_TIME=2000 \
  -e ZOO_INIT_LIMIT=10 \
  -e ZOO_SYNC_LIMIT=5 \
  -e ZOO_MAX_CLIENT_CNXNS=60 \
  -e ZOO_AUTOPURGE_PURGEINTERVAL=1 \
  -e ZOO_AUTOPURGE_SNAPRETAINCOUNT=3 \
  zookeeper:3.7

# 2. Start Kafka with explicit broker ID and replication setting
docker run -d --name new-kafka \
  --network host \
  -p 9092:9092 \
  -e KAFKA_BROKER_ID=2 \
  -e KAFKA_ZOOKEEPER_CONNECT=192.168.90.216:2181,192.168.90.76:2181 \
  -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.90.76:9092 \
  -e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=PLAINTEXT:PLAINTEXT \
  -e KAFKA_INTER_BROKER_LISTENER_NAME=PLAINTEXT \
  -e KAFKA_MIN_INSYNC_REPLICAS=1 \
  -e KAFKA_DEFAULT_REPLICATION_FACTOR=2 \
  -e KAFKA_AUTO_LEADER_REBALANCE_ENABLE=true \
  -e KAFKA_UNCLEAN_LEADER_ELECTION_ENABLE=true \
  --restart unless-stopped \
  wurstmeister/kafka:2.13-2.7.1

# 3. Start Pinot Controller with high availability settings
docker run -d \
  --name pinot-controller \
  --network host \
  -v /home/meghsham.kapure/Documents/SSP/copy_file:/pinot-files \
  -e BASE_DIR_PINOT=/pinot-files \
  -e "PINOT_CONTROLLER_HOST=0.0.0.0" \
  -e "PINOT_CONTROLLER_PORT=9000" \
  -e "PINOT_ZOOKEEPER_URL=192.168.90.216:2181,192.168.90.76:2181" \
  -e "PINOT_KAFKA_BROKER=192.168.90.216:9092,192.168.90.76:9092" \
  -e "PINOT_CONTROLLER_LEADER_ELECTION_ENABLED=true" \
  -e "PINOT_CONTROLLER_FAILURE_DETECTION_ENABLED=true" \
  -e "PINOT_CONTROLLER_FAILURE_DETECTION_INTERVAL_MS=30000" \
  -p 9000:9000 \
  apachepinot/pinot:latest \
  StartController

# 4. Start Pinot Broker with multiple controller endpoints
docker run -d --name pinot-broker \
  --network host \
  -v /home/meghsham.kapure/Documents/SSP/copy_file:/pinot-files \
  -e BASE_DIR_PINOT=/pinot-files \
  -e "PINOT_ZOOKEEPER_URL=192.168.90.216:2181,192.168.90.76:2181" \
  -e "PINOT_KAFKA_BROKER=192.168.90.216:9092,192.168.90.76:9092" \
  -e "PINOT_CONTROLLER_HOST=192.168.90.216,192.168.90.76" \
  -e "PINOT_CONTROLLER_PORT=9000" \
  -p 8099:8099 \
  apachepinot/pinot:latest \
  StartBroker

# 5. Start Pinot Server with redundancy
docker run -d --name pinot-server \
  --network host \
  -v /home/meghsham.kapure/Documents/SSP/copy_file:/pinot-files \
  -e BASE_DIR_PINOT=/pinot-files \
  -e "PINOT_ZOOKEEPER_URL=192.168.90.216:2181,192.168.90.76:2181" \
  -e "PINOT_CONTROLLER_HOST=192.168.90.216,192.168.90.76" \
  -e "PINOT_CONTROLLER_PORT=9000" \
  -p 8090:8090 \
  apachepinot/pinot:latest \
  StartServer
