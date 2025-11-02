# Q1. help me setup multi-node setup for apache pinot having 1 maseter and 2 slave nodes with docker

To set up a multi-node Apache Pinot cluster with 1 master (controller) and 2 slave (server) nodes using Docker, follow these steps. I'll guide you through creating the necessary `docker-compose.yml` configuration for a multi-node setup.

### Steps for Setting Up Apache Pinot with Docker

#### 1. **Set Up Docker Compose**

You'll need Docker and Docker Compose installed. If you haven't installed them yet, please follow the installation guide for your operating system.

#### 2. **Create a `docker-compose.yml` File**

Create a `docker-compose.yml` file in your project directory.

```yaml
version: '3'

services:
  # Master node (Pinot Controller)
  pinot-controller:
    image: apachepinot/pinot:latest
    container_name: pinot-controller
    environment:
      - PINOT_ZK_STR=pinot-zookeeper:2181
    ports:
      - "9000:9000"  # Pinot Controller UI
      - "8099:8099"  # Pinot Controller REST API
    depends_on:
      - pinot-zookeeper

  # Zookeeper (required for Pinot)
  pinot-zookeeper:
    image: wurstmeister/zookeeper:3.5.9
    container_name: pinot-zookeeper
    ports:
      - "2181:2181"
    environment:
      - ZOOKEEPER_TICK_TIME=2000
      - ZOOKEEPER_INIT_LIMIT=5
      - ZOOKEEPER_SYNC_LIMIT=2

  # 1st Slave node (Pinot Server)
  pinot-server-1:
    image: apachepinot/pinot:latest
    container_name: pinot-server-1
    environment:
      - PINOT_ZK_STR=pinot-zookeeper:2181
      - PINOT_CONTROLLER_HOST=pinot-controller
      - PINOT_SERVER=pinot-server
    ports:
      - "8090:8090"  # Pinot Server Port
      - "8091:8091"  # Pinot Query Port
    depends_on:
      - pinot-controller
      - pinot-zookeeper

  # 2nd Slave node (Pinot Server)
  pinot-server-2:
    image: apachepinot/pinot:latest
    container_name: pinot-server-2
    environment:
      - PINOT_ZK_STR=pinot-zookeeper:2181
      - PINOT_CONTROLLER_HOST=pinot-controller
      - PINOT_SERVER=pinot-server
    ports:
      - "8092:8092"  # Pinot Server Port
      - "8093:8093"  # Pinot Query Port
    depends_on:
      - pinot-controller
      - pinot-zookeeper

  # Kafka (optional, if you need Kafka for event streaming)
  kafka:
    image: wurstmeister/kafka:latest
    container_name: kafka
    environment:
      - KAFKA_ADVERTISED_LISTENER=PLAINTEXT://kafka:9093
      - KAFKA_LISTENER_SECURITY_PROTOCOL=PLAINTEXT
      - KAFKA_LISTENER_PORT=9093
      - KAFKA_LISTENER_NAME=PLAINTEXT
      - KAFKA_LISTENER_DEFAULT=PLAINTEXT
      - KAFKA_LISTENER_INTERCEPTOR_CLASSES=kafka.server.metrics.MetricsInterceptor
      - KAFKA_LISTENER_NAME_PLAINTEXT=PLAINTEXT
    ports:
      - "9093:9093"
    depends_on:
      - pinot-zookeeper
```

#### 3. **Explanation of the Configuration:**

- **pinot-controller**: The Pinot controller (master node) which manages the overall cluster state, segment management, and metadata.
- **pinot-zookeeper**: This is Zookeeper, which is used for coordination and metadata management for Pinot.
- **pinot-server-1 & pinot-server-2**: These are your slave nodes (Pinot servers) that handle the actual data storage and querying.
- **kafka** (Optional): If you are using Kafka to stream data into Pinot, this service will act as the Kafka broker.

#### 4. **Start the Cluster:**

After creating your `docker-compose.yml`, you can launch the entire setup using the following command:

```bash
docker-compose up -d
```

This will pull the necessary Docker images and start the cluster with the configured services (Controller, Zookeeper, and Servers).

#### 5. **Verify the Setup:**

After the services are up, you can verify the following:

- **Pinot Controller UI**: Open your browser and go to [http://localhost:9000](http://localhost:9000/). This will bring up the Pinot Controller UI where you can manage and monitor the cluster.
- **Pinot Servers**: You can also check if your server nodes are running properly via their respective ports:
    - Pinot Server 1: [http://localhost:8090](http://localhost:8090/)
    - Pinot Server 2: [http://localhost:8092](http://localhost:8092/)

#### 6. **Shut Down the Cluster:**

To shut down the setup, you can run:

```bash
docker-compose down
```

This will stop and remove the containers.


----
---


# Q2 : Video Summery for How to Create a Pinot Cluster | Apache Pinot 201 

## Deploying a cluster
1. Run Apache Pinot locally
2. Run Apache Pinot cluster using Docker Compose
3. Run Apache Pinot cluster on Kubernetes
4. Run Apache Pinot on Public Cloud
5. Run Managed Apache Pinot on StarTree Cloud (SaaS or BYOS)

## Apache Pinot Componets

![[AP Components.png]]

![[Docker Compose Deployement.png]]

![[Controller Config.png]]

![[Excercise.png]]

Steps : 
https://github.com/startreedata/learn/tree/main/pinot-advanced/01-create-cluster


# Q2 :  i want set up cluster in as way that db request goes through a single point and there load-balancer decide with node to get the data from depending upon the availability of the any other nodes

To set up your Apache Pinot cluster in a way that **database requests go through a single point**, and a **load balancer decides which node to fetch data from** depending on the **availability of the nodes**, you can achieve this by using **Pinot Broker** for routing queries and then placing a **load balancer** in front of the brokers to distribute the load across available nodes (Brokers) for high availability and fault tolerance.

Here's a step-by-step breakdown of how you can achieve this:

### 1. **Pinot Broker Layer for Query Routing**

Pinot's **Broker** nodes are responsible for handling client queries and routing them to the appropriate **Pinot Servers** based on the data they hold. The **Pinot Controller** manages the cluster, but it is the **Broker** nodes that handle the actual query processing, including querying and aggregating data from the **Pinot Servers**.

### 2. **Load Balancer for High Availability**

In this scenario, we want to introduce a **load balancer** that will distribute incoming client requests to different **Pinot Broker** nodes. The load balancer will handle the routing of queries and help ensure the system remains highly available and fault-tolerant.

You can use a **reverse proxy or load balancer** like **Nginx**, **HAProxy**, or a cloud-based load balancer (e.g., AWS ALB, Google Cloud Load Balancer) to manage incoming requests.

### 3. **Steps to Set Up Your Cluster with Load Balancer:**

#### A. **Cluster Setup with Multiple Brokers and Servers**

1. **Pinot Controller**: This remains as a single node or multiple nodes, depending on your need for redundancy. It’s responsible for cluster coordination, metadata management, and managing ingestion.
    
2. **Pinot Servers**: These are your worker nodes that store data and handle query execution. They serve data when a query is routed to them.
    
3. **Multiple Pinot Brokers**: You can scale out **Pinot Brokers** by running more than one instance. These brokers will be responsible for routing queries and aggregating data from the servers.
    
4. **Load Balancer**: Set up a load balancer that will distribute the incoming query requests to the available **Pinot Brokers**. The load balancer will decide which broker node should handle the query, and in the event of failure of one broker node, the load balancer will automatically redirect traffic to another broker.
    

#### B. **Docker Setup with Multiple Brokers and Load Balancer**

You can update your `docker-compose.yml` file to deploy **multiple Pinot Brokers** and add a **load balancer** to route traffic.

Here is an example of what your `docker-compose.yml` could look like:

```yaml
version: '3'

services:
  # Pinot Controller (Master Node)
  pinot-controller:
    image: apachepinot/pinot:latest
    container_name: pinot-controller
    environment:
      - PINOT_ZK_STR=pinot-zookeeper:2181
    ports:
      - "9000:9000"  # Pinot Controller UI
      - "8099:8099"  # Pinot Controller REST API
    depends_on:
      - pinot-zookeeper

  # Zookeeper (Required for Pinot)
  pinot-zookeeper:
    image: wurstmeister/zookeeper:3.5.9
    container_name: pinot-zookeeper
    ports:
      - "2181:2181"
    environment:
      - ZOOKEEPER_TICK_TIME=2000
      - ZOOKEEPER_INIT_LIMIT=5
      - ZOOKEEPER_SYNC_LIMIT=2

  # Pinot Server (Slave Nodes)
  pinot-server-1:
    image: apachepinot/pinot:latest
    container_name: pinot-server-1
    environment:
      - PINOT_ZK_STR=pinot-zookeeper:2181
      - PINOT_CONTROLLER_HOST=pinot-controller
      - PINOT_SERVER=pinot-server
    ports:
      - "8090:8090"  # Pinot Server Port
      - "8091:8091"  # Pinot Query Port
    depends_on:
      - pinot-controller
      - pinot-zookeeper

  pinot-server-2:
    image: apachepinot/pinot:latest
    container_name: pinot-server-2
    environment:
      - PINOT_ZK_STR=pinot-zookeeper:2181
      - PINOT_CONTROLLER_HOST=pinot-controller
      - PINOT_SERVER=pinot-server
    ports:
      - "8092:8092"  # Pinot Server Port
      - "8093:8093"  # Pinot Query Port
    depends_on:
      - pinot-controller
      - pinot-zookeeper

  # Pinot Broker (Multiple Brokers for High Availability)
  pinot-broker-1:
    image: apachepinot/pinot:latest
    container_name: pinot-broker-1
    environment:
      - PINOT_ZK_STR=pinot-zookeeper:2181
      - PINOT_CONTROLLER_HOST=pinot-controller
    ports:
      - "8094:8094"  # Pinot Broker Port
    depends_on:
      - pinot-controller
      - pinot-zookeeper

  pinot-broker-2:
    image: apachepinot/pinot:latest
    container_name: pinot-broker-2
    environment:
      - PINOT_ZK_STR=pinot-zookeeper:2181
      - PINOT_CONTROLLER_HOST=pinot-controller
    ports:
      - "8095:8095"  # Pinot Broker Port
    depends_on:
      - pinot-controller
      - pinot-zookeeper

  # Load Balancer (Nginx as an Example)
  nginx:
    image: nginx:latest
    container_name: pinot-load-balancer
    ports:
      - "8080:80"  # Load Balancer's Public Port
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf
    depends_on:
      - pinot-broker-1
      - pinot-broker-2
```

#### C. **Configure the Load Balancer (Nginx Example)**

The load balancer will be configured to distribute traffic between **Pinot Broker** nodes. Here’s an example `nginx.conf` configuration:

```nginx
http {
    upstream pinot_brokers {
        server pinot-broker-1:8094;
        server pinot-broker-2:8095;
    }

    server {
        listen 80;

        location / {
            proxy_pass http://pinot_brokers;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        }
    }
}
```

This configuration sets up Nginx to load balance between the two Pinot Brokers (`pinot-broker-1` and `pinot-broker-2`) by distributing incoming HTTP requests between them.

- **Upstream block** defines the two available brokers.
- **Proxy pass** routes the requests to the available brokers in the upstream pool.
- **Failover**: If one broker fails, the load balancer will redirect traffic to the other broker.

#### D. **Run the Cluster**

After configuring the `docker-compose.yml` and `nginx.conf` files, you can run the cluster:

```bash
docker-compose up -d
```

Once all containers are up and running:

- The **load balancer (Nginx)** will be available at [http://localhost:8080](http://localhost:8080/).
- This will route the queries to one of the available **Pinot Broker** nodes, ensuring high availability.
- The **Pinot Controller** will manage the metadata and cluster coordination, while the **Pinot Servers** will store and serve the data.

#### E. **Testing the Load Balancer**

You can test the load balancing by issuing queries to [http://localhost:8080](http://localhost:8080/). Each time you send a request, it should go through the load balancer and be forwarded to one of the available brokers. If a broker goes down, the load balancer will reroute traffic to the available broker.

### Summary:

- The **Pinot Controller** acts as the master node, managing metadata and coordinating the cluster.
- The **Pinot Servers** store data and handle query execution.
- The **Pinot Brokers** route the queries to the appropriate server, aggregating and returning results.
- The **Load Balancer (Nginx or HAProxy)** ensures high availability by distributing queries to available brokers, helping balance the load across multiple brokers.

Let me know if you need more help with setting this up or troubleshooting any issues!