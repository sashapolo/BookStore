#! /bin/bash

java \
 -cp target/lib:target/bookstore-client-1.0.jar \
 -Djava.rmi.server.codebase=file://$(pwd)/target/bookstore-client-1.0.jar \
 -Djava.rmi.server.useCodebaseOnly=false \
 -Djava.security.policy=client.policy \
 -Djava.rmi.server.hostname=localhost \
 edu.Main
