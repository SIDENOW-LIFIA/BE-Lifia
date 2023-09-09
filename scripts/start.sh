#!/bin/bash

PROJECT_ROOT_PATH="/home/ubuntu/lifia-github-action"
PROJECT_NAME="Lifia"
JAR_FILE="$PROJECT_ROOT_PATH/${PROJECT_NAME}-0.0.1-SNAPSHOT.jar"

APP_LOG="$PROJECT_ROOT_PATH/application.log"
ERROR_LOG="$PROJECT_ROOT_PATH/error.log"
START_LOG="$PROJECT_ROOT_PATH/start.log"

TIME_NOW=$(date +%c)

# build 파일 복사
echo "$TIME_NOW > $JAR_FILE 복사" >> $START_LOG
cp $PROJECT_ROOT_PATH/build/libs/*.jar $JAR_FILE

# jar 파일에 실행권한 추가
echo "> $JAR_FILE 에 실행권한 추가"
chmod +x $JAR_FILE

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 실행" >> $START_LOG
nohup java -jar $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $START_LOG