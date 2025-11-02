Sure! Below are the detailed commands that were executed, as well as the output you might see for each step based on your setup. I'll include some commands that might be useful for verifying and managing your containers, along with expected outputs.

### 1. **Running Zookeeper Container**

```bash
docker run -d --name new-zookeeper \
  --network my-network-pinot \
  -p 2181:2181 \
  zookeeper:3.7
```

**Expected Output:**

```
e88fd10b6b511ff17c0bd85f352a510541a0fe5f5d2499ff1edea9b0eba525b9
```

### 2. **Check Running Containers**

```bash
docker ps
```

**Expected Output:**

```
CONTAINER ID   IMAGE           COMMAND                  CREATED         STATUS         PORTS                                                                     NAMES
e88fd10b6b51   zookeeper:3.7   "/docker-entrypoint.…"   3 seconds ago   Up 2 seconds   2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp, :::2181->2181/tcp, 8080/tcp   new-zookeeper
```

### 3. **Running Kafka Container**

```bash
docker run -d --name new-kafka \
  --network host \
  -p 9092:9092 \
  -e KAFKA_BROKER_ID=2 \
  -e KAFKA_ZOOKEEPER_CONNECT=192.168.90.216:2181 \
  -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.90.76:9092 \
  -e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=PLAINTEXT:PLAINTEXT \
  -e KAFKA_INTER_BROKER_LISTENER_NAME=PLAINTEXT \
  --restart unless-stopped \
  wurstmeister/kafka:2.13-2.7.1
```

**Expected Output:**

```
8f307423bb657b493e4124f009831597c9d923e73a44af0674731f57b1ba2e9a
```

### 4. **Check Running Containers**

```bash
docker ps
```

**Expected Output:**

```
CONTAINER ID   IMAGE                           COMMAND                  CREATED          STATUS          PORTS                                                                     NAMES
8f307423bb65   wurstmeister/kafka:2.13-2.7.1   "start-kafka.sh"         3 seconds ago    Up 2 seconds                                                                              new-kafka
e88fd10b6b51   zookeeper:3.7                   "/docker-entrypoint.…"   15 seconds ago   Up 14 seconds   2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp, :::2181->2181/tcp, 8080/tcp   new-zookeeper
```

### 5. **Running Pinot Controller**

```bash
docker run -d --name pinot-controller \
  --network host \
  -v /home/meghsham.kapure/Documents/SSP/copy_file:/pinot-files \
  -e BASE_DIR_PINOT=/pinot-files \
  -e PINOT_CONTROLLER_HOST=0.0.0.0 \
  -e PINOT_ZOOKEEPER_URL=192.168.90.216:2181 \
  -e PINOT_KAFKA_BROKER=192.168.90.216:9092 \
  -p 9000:9000 \
  apachepinot/pinot:latest StartController
```

**Expected Output:**

```
563ca981fe8d61881bacebfb47212b5be5873a13c6a29fdc3c86d6ab9eb4c080
```

### 6. **Check Running Containers**

```bash
docker ps
```

**Expected Output:**

```
CONTAINER ID   IMAGE                           COMMAND                  CREATED          STATUS          PORTS                                                                     NAMES
563ca981fe8d   apachepinot/pinot:latest        "./bin/pinot-admin.s…"   2 seconds ago    Up 2 seconds                                                                              pinot-controller
8f307423bb65   wurstmeister/kafka:2.13-2.7.1   "start-kafka.sh"         24 seconds ago   Up 23 seconds                                                                             new-kafka
e88fd10b6b51   zookeeper:3.7                   "/docker-entrypoint.…"   36 seconds ago   Up 35 seconds   2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp, :::2181->2181/tcp, 8080/tcp   new-zookeeper
```

### 7. **Running Pinot Broker**

```bash
docker run -d --name pinot-broker \
  --network host \
  -v /home/meghsham.kapure/Documents/SSP/copy_file:/pinot-files \
  -e BASE_DIR_PINOT=/pinot-files \
  -e PINOT_ZOOKEEPER_URL=new-zookeeper:2181 \
  -e PINOT_KAFKA_BROKER=192.168.90.216:9092 \
  -e PINOT_CONTROLLER_HOST=192.168.90.216:9000 \
  -p 8099:8099 \
  apachepinot/pinot:latest StartBroker
```

**Expected Output:**

```
f621079a3bcf5a55be6d80f9a4fdf83a6b03d6f679c130ca7688baf6eb268625
```

### 8. **Check Running Containers**

```bash
docker ps
```

**Expected Output:**

```
CONTAINER ID   IMAGE                           COMMAND                  CREATED              STATUS              PORTS                                                                     NAMES
d8c43f9f531e   apachepinot/pinot:latest        "./bin/pinot-admin.s…"   8 seconds ago        Up 7 seconds                                                                                  pinot-server
f621079a3bcf   apachepinot/pinot:latest        "./bin/pinot-admin.s…"   24 seconds ago       Up 24 seconds                                                                                 pinot-broker
563ca981fe8d   apachepinot/pinot:latest        "./bin/pinot-admin.s…"   37 seconds ago       Up 37 seconds                                                                                 pinot-controller
8f307423bb65   wurstmeister/kafka:2.13-2.7.1   "start-kafka.sh"         59 seconds ago       Up 58 seconds                                                                                 new-kafka
e88fd10b6b51   zookeeper:3.7                   "/docker-entrypoint.…"   About a minute ago   Up About a minute   2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp, :::2181->2181/tcp, 8080/tcp   new-zookeeper
```

### 9. **Running Second Pinot Server**

```bash
docker run -d --name pinot-server-2 \
  --network host \
  -v /home/meghsham.kapure/Documents/SSP/copy_file:/pinot-files \
  -e BASE_DIR_PINOT=/pinot-files \
  -e "PINOT_ZOOKEEPER_URL=192.168.90.216:2181" \
  -e "PINOT_CONTROLLER_HOST=192.168.90.216:9000" \
  -p 8091:8091 \
  apachepinot/pinot:latest \
  StartServer
```

**Expected Output:**

```
f5ae8b37ed5096519877804fa48d0aaaa0c7ab608bba3e01b0fe44a1cbc2fdde
```

### 10. **Check Running Containers Again**

```bash
docker ps
```

**Expected Output:**

```
CONTAINER ID   IMAGE                           COMMAND                  CREATED          STATUS          PORTS                                                                     NAMES
d8c43f9f531e   apachepinot/pinot:latest        "./bin/pinot-admin.s…"   45 minutes ago   Up 45 minutes                                                                             pinot-server
f621079a3bcf   apachepinot/pinot:latest        "./bin/pinot-admin.s…"   45 minutes ago   Up 45 minutes                                                                             pinot-broker
563ca981fe8d   apachepinot/pinot:latest        "./bin/pinot-admin.s…"   45 minutes ago   Up 45 minutes                                                                             pinot-controller
8f307423bb65   wurstmeister/kafka:2.13-2.7.1   "start-kafka.sh"         46 minutes ago   Up 46 minutes                                                                             new-kafka
e88fd10b6b51   zookeeper:3.7                   "/docker-entrypoint.…"   46 minutes ago   Up 46 minutes   2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp, :::2181->2181/tcp, 8080/tcp   new-zookeeper
```

### Verification Commands

You can run the following commands to verify and troubleshoot the containers:

#### Checking Logs for a Container (e.g., Pinot Controller)

```bash
docker logs pinot-controller
```

#### Checking the Health of a Container

```bash
docker inspect --format='{{json .State.Health}}' pinot-server
```

#### Stopping a Container

```bash
docker stop <container_name>
```

#### Removing a Container

```bash
docker rm <container_name>
```

Let me know if you need more details on any specific part of the setup!

Replic




meghsham.kapure@meghsham-ThinkPad-T460:/$   

docker run -d --name new-zookeeper \
  --network my-network-pinot \
  -p 2181:2181 \
  zookeeper:3.7


e88fd10b6b511ff17c0bd85f352a510541a0fe5f5d2499ff1edea9b0eba525b9

meghsham.kapure@meghsham-ThinkPad-T460:/$ 
docker ps 

CONTAINER ID   IMAGE           COMMAND                  CREATED         STATUS         PORTS                                                                     NAMES
e88fd10b6b51   zookeeper:3.7   "/docker-entrypoint.…"   3 seconds ago   Up 2 seconds   2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp, :::2181->2181/tcp, 8080/tcp   new-zookeeper

=========================================================================================================================================

meghsham.kapure@meghsham-ThinkPad-T460:/$ 

docker run -d --name new-kafka \
--network host \
-p 9092:9092 \
-e KAFKA_BROKER_ID=2 \
-e KAFKA_ZOOKEEPER_CONNECT=192.168.90.216:2181 \
-e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.90.76:9092 \
-e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=PLAINTEXT:PLAINTEXT \
-e KAFKA_INTER_BROKER_LISTENER_NAME=PLAINTEXT \
--restart unless-stopped \
wurstmeister/kafka:2.13-2.7.1

WARNING: Published ports are discarded when using host network mode
8f307423bb657b493e4124f009831597c9d923e73a44af0674731f57b1ba2e9a

meghsham.kapure@meghsham-ThinkPad-T460:/$ 
docker ps 

CONTAINER ID   IMAGE                           COMMAND                  CREATED          STATUS          PORTS                                                                     NAMES
8f307423bb65   wurstmeister/kafka:2.13-2.7.1   "start-kafka.sh"         3 seconds ago    Up 2 seconds                                                                              new-kafka
e88fd10b6b51   zookeeper:3.7                   "/docker-entrypoint.…"   15 seconds ago   Up 14 seconds   2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp, :::2181->2181/tcp, 8080/tcp   new-zookeeper

=========================================================================================================================================


meghsham.kapure@meghsham-ThinkPad-T460:/$   

docker run -d \
  --name pinot-controller \
  --network host \
  -v /home/meghsham.kapure/Documents/SSP/copy_file:/pinot-files \
  -e BASE_DIR_PINOT=/pinot-files \
  -e PINOT_CONTROLLER_HOST=0.0.0.0 \
  -e PINOT_ZOOKEEPER_URL=192.168.90.216:2181 \
  -e PINOT_KAFKA_BROKER=192.168.90.216:9092 \
  -p 9000:9000 \
  apachepinot/pinot:latest StartController


WARNING: Published ports are discarded when using host network mode
563ca981fe8d61881bacebfb47212b5be5873a13c6a29fdc3c86d6ab9eb4c080

meghsham.kapure@meghsham-ThinkPad-T460:/$ 
docker ps 

CONTAINER ID   IMAGE                           COMMAND                  CREATED          STATUS          PORTS                                                                     NAMES
563ca981fe8d   apachepinot/pinot:latest        "./bin/pinot-admin.s…"   2 seconds ago    Up 2 seconds                                                                              pinot-controller
8f307423bb65   wurstmeister/kafka:2.13-2.7.1   "start-kafka.sh"         24 seconds ago   Up 23 seconds                                                                             new-kafka
e88fd10b6b51   zookeeper:3.7                   "/docker-entrypoint.…"   36 seconds ago   Up 35 seconds   2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp, :::2181->2181/tcp, 8080/tcp   new-zookeeper

=========================================================================================================================================


meghsham.kapure@meghsham-ThinkPad-T460:/$ 

docker run -d --name pinot-broker \
  --network host \
  -v /home/meghsham.kapure/Documents/SSP/copy_file:/pinot-files \
  -e BASE_DIR_PINOT=/pinot-files \
  -e PINOT_ZOOKEEPER_URL=new-zookeeper:2181 \
  -e PINOT_KAFKA_BROKER=192.168.90.216:9092 \
  -e PINOT_CONTROLLER_HOST=192.168.90.216:9000 \
  -p 8099:8099 \
  apachepinot/pinot:latest StartBroker

WARNING: Published ports are discarded when using host network mode
f621079a3bcf5a55be6d80f9a4fdf83a6b03d6f679c130ca7688baf6eb268625

=========================================================================================================================================


meghsham.kapure@meghsham-ThinkPad-T460:/$  

docker run -d --name pinot-server \
  --network host \
  -v /home/meghsham.kapure/Documents/SSP/copy_file:/pinot-files \
  -e BASE_DIR_PINOT=/pinot-files \
  -e "PINOT_ZOOKEEPER_URL=192.168.90.216:2181" \
  -e "PINOT_CONTROLLER_HOST=192.168.90.216:9000" \
  -p 8090:8090 \
  apachepinot/pinot:latest \
  StartServer

WARNING: Published ports are discarded when using host network mode
d8c43f9f531e11008a0f295d2c925e77b034790216c8d2775f4e8b03edf69290


meghsham.kapure@meghsham-ThinkPad-T460:/$ 

docker ps 

CONTAINER ID   IMAGE                           COMMAND                  CREATED              STATUS              PORTS                                                                     NAMES
d8c43f9f531e   apachepinot/pinot:latest        "./bin/pinot-admin.s…"   8 seconds ago        Up 7 seconds                                                                                  pinot-server
f621079a3bcf   apachepinot/pinot:latest        "./bin/pinot-admin.s…"   24 seconds ago       Up 24 seconds                                                                                 pinot-broker
563ca981fe8d   apachepinot/pinot:latest        "./bin/pinot-admin.s…"   37 seconds ago       Up 37 seconds                                                                                 pinot-controller
8f307423bb65   wurstmeister/kafka:2.13-2.7.1   "start-kafka.sh"         59 seconds ago       Up 58 seconds                                                                                 new-kafka
e88fd10b6b51   zookeeper:3.7                   "/docker-entrypoint.…"   About a minute ago   Up About a minute   2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp, :::2181->2181/tcp, 8080/tcp   new-zookeeper


=========================================================================================================================================


meghsham.kapure@meghsham-ThinkPad-T460:/$   

docker run -d --name pinot-server-2 \
  --network host \
  -v /home/meghsham.kapure/Documents/SSP/copy_file:/pinot-files \
  -e BASE_DIR_PINOT=/pinot-files \
  -e "PINOT_ZOOKEEPER_URL=192.168.90.216:2181" \
  -e "PINOT_CONTROLLER_HOST=192.168.90.216:9000" \
  -p 8091:8091 \
  apachepinot/pinot:latest \
  StartServer


WARNING: Published ports are discarded when using host network mode
f5ae8b37ed5096519877804fa48d0aaaa0c7ab608bba3e01b0fe44a1cbc2fdde

meghsham.kapure@meghsham-ThinkPad-T460:/$ 

docker ps

CONTAINER ID   IMAGE                           COMMAND                  CREATED          STATUS          PORTS                                                                     NAMES
d8c43f9f531e   apachepinot/pinot:latest        "./bin/pinot-admin.s…"   45 minutes ago   Up 45 minutes                                                                             pinot-server
f621079a3bcf   apachepinot/pinot:latest        "./bin/pinot-admin.s…"   45 minutes ago   Up 45 minutes                                                                             pinot-broker
563ca981fe8d   apachepinot/pinot:latest        "./bin/pinot-admin.s…"   45 minutes ago   Up 45 minutes                                                                             pinot-controller
8f307423bb65   wurstmeister/kafka:2.13-2.7.1   "start-kafka.sh"         46 minutes ago   Up 46 minutes                                                                             new-kafka
e88fd10b6b51   zookeeper:3.7                   "/docker-entrypoint.…"   46 minutes ago   Up 46 minutes   2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp, :::2181->2181/tcp, 8080/tcp   new-zookeeper


=========================================================================================================================================

# dicontinued above controller, created below, tried data ingestion from my machine, IT WORKS.

meghsham.kapure@meghsham-ThinkPad-T460:/$ 

docker run -d \
  --name pinot-controller-2 \
  --network host\
  -v /home/meghsham.kapure/Documents/SSP/copy_file:/pinot-files \
  -e BASE_DIR_PINOT=/pinot-files \
  -e "PINOT_CONTROLLER_HOST=0.0.0.0" \
  -e "PINOT_ZOOKEEPER_URL=new-zookeeper:2181" \
  -e "PINOT_KAFKA_BROKER=192.168.90.76:9092" \
  -p 90001:9001 \
   apachepinot/pinot:latest \
  StartController

WARNING: Published ports are discarded when using host network mode
df3f01bb9c4aabb579f648d26a857ec83d73332e40771f18f4cefe5d09d090a9

meghsham.kapure@meghsham-ThinkPad-T460:/$ docker ps

CONTAINER ID   IMAGE                           COMMAND                  CREATED             STATUS             PORTS                                                                     NAMES
df3f01bb9c4a   apachepinot/pinot:latest        "./bin/pinot-admin.s…"   7 seconds ago       Up 7 seconds                                                                                 pinot-controller-2
d8c43f9f531e   apachepinot/pinot:latest        "./bin/pinot-admin.s…"   About an hour ago   Up About an hour                                                                             pinot-server
f621079a3bcf   apachepinot/pinot:latest        "./bin/pinot-admin.s…"   About an hour ago   Up About an hour                                                                             pinot-broker
8f307423bb65   wurstmeister/kafka:2.13-2.7.1   "start-kafka.sh"         About an hour ago   Up About an hour                                                                             new-kafka
e88fd10b6b51   zookeeper:3.7                   "/docker-entrypoint.…"   About an hour ago   Up About an hour   2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp, :::2181->2181/tcp, 8080/tcp   new-zookeeper

# The docker system df command displays information regarding the amount of disk space used by the Docker daemon.
meghsham.kapure@meghsham-ThinkPad-T460:/$ docker logs df 

WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by org.codehaus.groovy.reflection.CachedClass (file:/opt/pinot/lib/pinot-all-1.4.0-SNAPSHOT-jar-with-dependencies.jar) to method java.lang.Object.finalize()
WARNING: Please consider reporting this to the maintainers of org.codehaus.groovy.reflection.CachedClass
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
2025/02/19 06:56:19.756 INFO [StartControllerCommand] [main] Executing command: StartController -clusterName PinotCluster -controllerHost null -controllerPort 9000 -dataDir /tmp/data/PinotController -zkAddress localhost:2181
2025/02/19 06:56:19.786 INFO [StartServiceManagerCommand] [main] Executing command: StartServiceManager -clusterName PinotCluster -zkAddress localhost:2181 -port -1 -bootstrapServices []
2025/02/19 06:56:19.787 INFO [StartServiceManagerCommand] [main] Starting a Pinot [SERVICE_MANAGER] at 0.743s since launch
2025/02/19 06:56:19.790 INFO [StartServiceManagerCommand] [main] Started Pinot [SERVICE_MANAGER] instance [ServiceManager_meghsham-ThinkPad-T460_-1] at 0.747s since launch
2025/02/19 06:56:19.791 INFO [StartServiceManagerCommand] [main] Starting a Pinot [CONTROLLER] at 0.747s since launch
2025/02/19 06:56:25.154 ERROR [DelayedAutoRebalancer] [HelixController-pipeline-default-PinotCluster-(7bc7e06b_DEFAULT)] No instances or active instances available for resource leadControllerResource, allInstances: [Controller_192.168.90.76_9000], liveInstances: [], activeInstances: []
2025/02/19 06:56:25.229 ERROR [DelayedAutoRebalancer] [HelixController-pipeline-default-PinotCluster-(3d547fe7_DEFAULT)] No instances or active instances available for resource leadControllerResource, allInstances: [Controller_192.168.90.76_9000], liveInstances: [], activeInstances: []
2025/02/19 06:56:25.259 ERROR [DelayedAutoRebalancer] [HelixController-pipeline-default-PinotCluster-(c16e138e_DEFAULT)] No instances or active instances available for resource leadControllerResource, allInstances: [Controller_192.168.90.76_9000], liveInstances: [], activeInstances: []
2025/02/19 06:56:25.295 ERROR [DelayedAutoRebalancer] [HelixController-pipeline-default-PinotCluster-(4fd12740_DEFAULT)] No instances or active instances available for resource leadControllerResource, allInstances: [Controller_192.168.90.76_9000], liveInstances: [], activeInstances: []
2025/02/19 06:56:25.338 ERROR [DelayedAutoRebalancer] [HelixController-pipeline-default-PinotCluster-(0719afe8_DEFAULT)] No instances or active instances available for resource leadControllerResource, allInstances: [Controller_192.168.90.76_9000], liveInstances: [], activeInstances: []
2025/02/19 06:56:25.366 ERROR [DelayedAutoRebalancer] [HelixController-pipeline-default-PinotCluster-(c195125f_DEFAULT)] No instances or active instances available for resource leadControllerResource, allInstances: [Controller_192.168.90.76_9000], liveInstances: [], activeInstances: []
2025/02/19 06:56:25.395 ERROR [DelayedAutoRebalancer] [HelixController-pipeline-default-PinotCluster-(1f667c32_DEFAULT)] No instances or active instances available for resource leadControllerResource, allInstances: [Controller_192.168.90.76_9000], liveInstances: [], activeInstances: []
2025/02/19 06:56:25.433 ERROR [DelayedAutoRebalancer] [HelixController-pipeline-default-PinotCluster-(e3e84443_DEFAULT)] No instances or active instances available for resource leadControllerResource, allInstances: [Controller_192.168.90.76_9000], liveInstances: [], activeInstances: []
2025/02/19 06:56:25.503 ERROR [DelayedAutoRebalancer] [HelixController-pipeline-default-PinotCluster-(acb58e19_DEFAULT)] No instances or active instances available for resource leadControllerResource, allInstances: [Controller_192.168.90.76_9000], liveInstances: [], activeInstances: []
2025/02/19 06:56:25.553 ERROR [DelayedAutoRebalancer] [HelixController-pipeline-default-PinotCluster-(4895761c_DEFAULT)] No instances or active instances available for resource leadControllerResource, allInstances: [Controller_192.168.90.76_9000], liveInstances: [], activeInstances: []
Feb 19, 2025 6:56:36 AM org.glassfish.grizzly.http.server.NetworkListener start
INFO: Started listener bound to [0.0.0.0:9000]
Feb 19, 2025 6:56:36 AM org.glassfish.grizzly.http.server.HttpServer start
INFO: [HttpServer] Started.

=========================================================================================================================================

docker run -d \
  --name pinot-controller-3 \
  --network host\
  -v /home/meghsham.kapure/Documents/SSP/copy_file:/pinot-files \
  -e BASE_DIR_PINOT=/pinot-files \
  -e "PINOT_CONTROLLER_HOST=0.0.0.0" \
  -e "PINOT_ZOOKEEPER_URL=new-zookeeper:2181" \
  -e "PINOT_KAFKA_BROKER=192.168.90.76:9092" \
  -p 9001:9000\
   apachepinot/pinot:latest \
  StartController



  docker run -d --name pinot-controller-4 -p 9000:9000 apachepinot/pinot:latest \
StartController -zkAddress 192.168.1.216:2181 -clusterName PinotCluster

docker cp /home/meghsham.kapure/Documents/SSP/copy_file/SchemaRealTime.json pinot-controller-4:/tmp/SchemaRealTime.json

docker cp /home/meghsham.kapure/Documents/SSP/copy_file/tableConfigRealTime.json pinot-controller-4:/tmp/tableConfigRealTime.json

docker exec -it pinot-controller-4 /bin/bash -c   "bin/pinot-admin.sh AddTable \
  -tableConfigFile /tmp/tableConfigRealTime.json \
  -schemaFile /tmp/SchemaRealTime.json \
  -controllerPort 9000 -exec"




docker run -d \
  --name pinot-controller-4 \
  --network host\
  -v /home/meghsham.kapure/Documents/SSP/copy_file:/pinot-files \
  -e BASE_DIR_PINOT=/pinot-files \
  -e "PINOT_CONTROLLER_HOST=0.0.0.0" \
  -e "PINOT_ZOOKEEPER_URL=new-zookeeper:2181" \
  -e "PINOT_KAFKA_BROKER=192.168.90.216:9092" \
  -p 9000:9000\
   apachepinot/pinot:latest \
  StartController
