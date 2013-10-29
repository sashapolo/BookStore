#! /bin/bash

java \
 -Djava.rmi.server.codebase=file:$(pwd)/target/bookstore-client-1.0.jar \
 -Djava.security.policy=client.policy \
 -jar target/bookstore-client-1.0.jar

