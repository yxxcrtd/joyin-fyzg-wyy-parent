#!/bin/sh

## java env
API_NAME=fyzg-wyy-app-service-0.0.1-SNAPSHOT
API_CONFIG=.,resources,lib
JAR_NAME=$API_NAME.jar
PID=$API_NAME.pid
PRO_FILE=$3

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
    cd ..
    if [ ! -n "$PRO_FILE" ] ;then
     echo "java -Xss1m -Xmx128m -Dspring.cloud.config.profile=prod -jar" -Dloader.path=$API_CONFIG $JAR_NAME
     nohup java -Xss1m -Xmx2g -Dspring.cloud.config.profile=prod -jar -Dloader.path=$API_CONFIG $JAR_NAME >/dev/null 2>&1 &
    else
     echo "the profile is $PRO_FILE"
     echo "java -Xss1m -Xmx128m -Dspring.cloud.config.profile=$PRO_FILE -jar" -Dloader.path=$API_CONFIG $JAR_NAME
     nohup java -Xss1m -Xmx256m -Dspring.cloud.config.profile=$PRO_FILE -jar -Dloader.path=$API_CONFIG $JAR_NAME >/dev/null 2>&1 &
    fi
    echo $! > $PID
    echo ">>> start $JAR_NAME successed PID=$! <<<"
   fi
  }

stop(){
  #is_exist
  pidf=$(cat $PID)
  #echo "$pidf"
  echo ">>> api PID = $pidf begin kill $pidf <<<"
  kill $pidf
  rm -rf $PID
  sleep 2
  is_exist
  if [ $? -eq "0" ]; then
    echo ">>> api 2 PID = $pid begin kill -9 $pid  <<<"
    kill -9  $pid
    sleep 2
    echo ">>> $JAR_NAME process stopped <<<"
  else
    echo ">>> ${JAR_NAME} is not running <<<"
  fi
}

status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo ">>> ${JAR_NAME} is running PID is ${pid} <<<"
  else
    echo ">>> ${JAR_NAME} is not running <<<"
  fi
}

restart(){
  stop
  start
}

case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac
exit 0