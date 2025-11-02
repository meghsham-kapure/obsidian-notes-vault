
    
    docker run --rm -ti \
    --network=pinot-demo \
    --name pinot-server \
    -p 8001:8001 \
    -e JAVA_OPTS="-Dplugins.dir=/opt/pinot/plugins -Xms4G -Xmx16G -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -Xloggc:gc-pinot-server.log" \
    d apachepinot/pinot:latest StartServer \
    -zkAddress pinot-zookeeper:2181