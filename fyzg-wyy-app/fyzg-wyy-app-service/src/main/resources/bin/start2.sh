#!/bin/sh

## java env
API_NAME=fyzg-wyy-app-service-0.0.1-SNAPSHOT
API_CONFIG=.,resources,lib
JAR_NAME=$API_NAME.jar
PID=$API_NAME.pid
PRO_FILE=$1

usage() {
    echo "Usage: sh startup.sh [start|stop|restart|status]"
    exit 1
}

is_exist(){
  pid=`ps -ef|grep $JAR_NAME|grep -v grep|awk '{print $2}' `
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

start(){
  is_exist
  if [ $? -eq "0" ]; then
    echo ">>> ${JAR_NAME} is already running PID=${pid} <<<"
  else
    echo 'JAVA_HOME is $JAVA_HOME'
    cd /data/server/fyzg-wyy-app-service
    if [ ! -n "$PRO_FILE" ] ;then
     echo "java -Xss1m -Xmx128m -Dspring.cloud.config.profile=prod -jar" -Dloader.path=$API_CONFIG $JAR_NAME
     nohup java -Xss1m -Xmx256m -Dspring.cloud.config.profile=prod -jar -Dloader.path=$API_CONFIG $JAR_NAME >execute.log 2>&1 &
     tail -f execute.log
    else
     echo "the profile is $PRO_FILE"
     echo "java -Xss1m -Xmx128m -Dspring.cloud.config.profile=$PRO_FILE -jar" -Dloader.path=$API_CONFIG $JAR_NAME
     nohup java -Xss1m -Xmx256m -Dspring.cloud.config.profile=$PRO_FILE -jar -Dloader.path=$API_CONFIG $JAR_NAME >execute.log 2>&1 &
     tail -f execute.log
    fi
    echo $! > $PID
    echo ">>> start $JAR_NAME successed PID=$! <<<"
  fi
}

start
