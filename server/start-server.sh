#! /bin/bash

java \
 -Djava.rmi.server.codebase=file://$(pwd)/target/bookstore-server-1.0.jar \
 -Djava.security.policy=server.policy \
 -Djava.rmi.server.hostname=127.0.0.1 \
 -jar target/bookstore-server-1.0.jar

