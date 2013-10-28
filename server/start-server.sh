#! /bin/bash

java -cp "../common/target/*" \
 -Djava.rmi.server.codebase=http://$(hostname)/~$(whoami)/target/bookstore-server-1.0.jar \
 -Djava.rmi.server.hostname=$(hostname) \
 -Djava.security.policy=server.policy \
 -jar target/bookstore-server-1.0.jar

