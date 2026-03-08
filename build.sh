#!/bin/bash
export JAVA_HOME=$(dirname $(dirname $(readlink -f $(which java))))
echo "JAVA_HOME=$JAVA_HOME"
./mvnw -B -DskipTests clean install
