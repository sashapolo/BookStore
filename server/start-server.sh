#! /bin/bash

java \
 -Djava.rmi.server.codebase=file:$(pwd)/target/bookstore-server-1.0.jar \
 -Djava.security.policy=server.policy \
 -jar target/bookstore-server-1.0.jar

