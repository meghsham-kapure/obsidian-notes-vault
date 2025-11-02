Zookeeper Pulled






meghsham.kapure@meghsham-ThinkPad-T460:~$ docker run \
    -p 2123:2123 \
    -p 9000:9000 \
    -p 8000:8000 \
    -p 7050:7050 \
    -p 6000:6000 \
    apachepinot/pinot:latest QuickStart \
    -type batch


```


```




### Commands:

Here’s a recap of your working commands:

#### 1. **Pinot Controller:**

```bash
docker run --rm -ti \
    --network=pinot-demo \
    --name pinot-controller \
    -p 9001:9001 \
    -e JAVA_OPTS="-Dplugins.dir=/opt/pinot/plugins -Xms1G -Xmx4G -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -Xloggc:gc-pinot-controller.log" \
    -d apachepinot/pinot:latest StartController \
    -zkAddress pinot-zookeeper:2181
```

#### 2. **Pinot Broker (Fixed Image Name):**

```bash
docker run --rm -ti \
    --network=pinot-demo \
    --name pinot-broker \
    -p 7001:7001 \
    -e JAVA_OPTS="-Dplugins.dir=/opt/pinot/plugins -Xms4G -Xmx4G -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -Xloggc:gc-pinot-broker.log" \
    -d apachepinot/pinot:latest StartBroker \
    -zkAddress pinot-zookeeper:2181
```

#### 3. **Pinot Server (Fixed Typo):**

```bash
docker run --rm -ti \
    --network=pinot-demo \
    --name pinot-server \
    -p 8001:8001 \
    -e JAVA_OPTS="-Dplugins.dir=/opt/pinot/plugins -Xms4G -Xmx16G -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -Xloggc:gc-pinot-server.log" \
    -d apachepinot/pinot:latest StartServer \
    -zkAddress pinot-zookeeper:2181
```

### Status:

- You’ve successfully run the `pinot-controller`, `pinot-broker`, and `pinot-server` containers.
- The containers are likely running, but you can check if they are healthy or if there are any errors.

### Check Running Containers:

To confirm everything is up and running, use the following command to check the status of the containers:

```bash
docker ps
```

This will show you all the running containers, and you should see the `pinot-controller`, `pinot-broker`, and `pinot-server` containers listed with their status.

### Accessing the Services:

- The Pinot controller is exposed on port `9001`.
- The Pinot broker is exposed on port `7001`.
- The Pinot server is exposed on port `8001`.

You can access these services via their respective ports.
