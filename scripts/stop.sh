 #!/bin/bash

PROJECT_ROOT_PATH="/home/ubuntu/app/deploy/"
PROJECT_NAME="Lifia"
JAR_FILE="$PROJECT_ROOT_PATH/${PROJECT_NAME}.jar"
STOP_LOG="$PROJECT_ROOT_PATH/stop.log"

TIME_NOW=$(date +%c)

# 현재 구동 중인 애플리케이션 pid 확인
CURRENT_PID=$(pgrep -f $JAR_FILE)

# 프로세스가 켜져 있으면 종료
if [ -z $CURRENT_PID ]; then
  echo "$TIME_NOW > 현재 실행중인 애플리케이션이 없습니다" >> $STOP_LOG
else
  echo "$TIME_NOW > 실행중인 $CURRENT_PID 애플리케이션 종료 " >> $STOP_LOG
  kill -9 $CURRENT_PID
fi