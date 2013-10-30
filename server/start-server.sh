#! /bin/bash

java \
 -cp target/lib:target/bookstore-server-1.0.jar \
 -Djava.rmi.server.codebase=file://$(pwd)/target/bookstore-server-1.0.jar \
 -Djava.rmi.server.useCodebaseOnly=false \
 -Djava.security.policy=server.policy \
 -Djava.rmi.server.hostname=localhost \
 edu.Main
