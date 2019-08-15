#!/bin/bash

set -e

APP_NAME="http-slick-demo" #更改为项目名 与./target/universal/stage/bin/ 下的0110文件名保持一致
ROOT=$(dirname $(readlink -e $0))
# SCRIPT=$ROOT/bin/com_chinamobiad_adx_edge_main
SCRIPT=$ROOT/bin/$APP_NAME
LOG_XML="$ROOT/config/logback.xml"

if [ -f $LOG_XML ]; then
    LOG_CONFIG="-Dlogback.configurationFile=$LOG_XML"
else
    LOG_CONFIG=""
fi

CONFIG_FILE="$ROOT/config/application.conf"

if [ -f $CONFIG_FILE ]; then
    CONFIG="-Dconfig.file=$ROOT/config/application.conf"
else
    CONFIG=""
fi

if [ -z "$2" ]; then
    N=0
else
    N=$2
fi

NN=$(printf %02d $N)

CC="-Dnode=$N -Dhttp.port=44$NN -Dakka.remote.netty.tcp.port=144$NN" # -Dio.netty.tryReflectionSetAccessible=true --add-exports java.base/jdk.internal.misc=ALL-UNNAMED"

ID=$(uuidgen)
EXTRA="-Dapp=$ID"
RUN_DIR="/run/Demo/${APP_NAME}/$N" #随意更改‘Demo’
mkdir -pv ${RUN_DIR}
PID_FILE_NAME=${RUN_DIR}/${APP_NAME}-${ID}.pid

export JAVA_OPTS="-Xmx8G -Xms2G -server -XX:+UseG1GC -XX:MaxGCPauseMillis=100 $JAVA_OPTS"

RUN_COMMAND="bash $SCRIPT $CC $CONFIG $LOG_CONFIG $EXTRA"

case $1 in
    start | run)
        $RUN_COMMAND
 	;;
    server)
 	    nohup $RUN_COMMAND > /dev/null 2>&1 &
        sleep 2
        pid=$(ps -ef | grep "$ID" | grep -v grep | awk '{print $2}')
        if [ -n "$pid" ]; then
            echo $pid > $PID_FILE_NAME
            echo "server started. pid: $pid"
        else
            echo "starting failed!"
        fi
        ;;
    stop)
        fs=$(ls $RUN_DIR | grep "${APP_NAME}-")
        for i in $(ls $RUN_DIR | grep "${APP_NAME}-")
        do
            pid=$(cat $RUN_DIR/$i)
            kill $pid || echo "process $pid not exist."
            rm -vfr $RUN_DIR/$i
            echo "service stopped. pid: $pid"
        done
        ;;
    *)
        $RUN_COMMAND
        ;;
esac

exit 0
#sudo ./target/universal/stage/app.sh run