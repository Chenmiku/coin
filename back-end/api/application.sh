#!/bin/bash

BASE_PATH=/home/centos/application
BUILD_PATH=$(ls $BASE_PATH/jar/*.jar)
JAR_NAME=$(basename $BUILD_PATH)
echo "> build 파일명: $JAR_NAME"

echo "> build 파일 복사"
DEPLOY_PATH=$BASE_PATH/deploy/
cp $BUILD_PATH $DEPLOY_PATH

echo "> 현재 구동중인 Set 확인"
CURRENT_PROFILE=$(curl -s http://localhost/process-env)
echo "> process env : $CURRENT_PROFILE"

# 쉬고 있는 set 찾기: api-prod1이 사용중이면 api-prod2가 쉬고 있고, 반대면 api-prod1이 쉬고 있음
if [ $CURRENT_PROFILE == api-prod1 ]
then
  IDLE_PROFILE=api-prod2
  IDLE_PROFILE_KILL=api-prod1
  IDLE_PORT=18086
elif [ $CURRENT_PROFILE == api-prod2 ]
then
  IDLE_PROFILE=api-prod1
  IDLE_PROFILE_KILL=api-prod2
  IDLE_PORT=18087
else
  echo "> 일치하는 Profile이 없습니다. Profile: $CURRENT_PROFILE"
  echo "> api-prod1을 할당합니다. IDLE_PROFILE: api-prod1"
  IDLE_PROFILE=api-prod1
  IDLE_PROFILE_KILL=api-prod2
  IDLE_PORT=18087
fi

echo "> application.jar 교체"
IDLE_APPLICATION=$IDLE_PROFILE-springboot-webservice.jar
IDLE_APPLICATION_PATH=$DEPLOY_PATH$IDLE_APPLICATION

ln -Tfs $DEPLOY_PATH$JAR_NAME $IDLE_APPLICATION_PATH

echo "> $IDLE_APPLICATION 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID=$(pgrep -f $IDLE_APPLICATION)

if [ -z $IDLE_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> sudo kill -15 $IDLE_PID"
  sudo kill -15 $IDLE_PID
  sleep 5
fi

echo "> $IDLE_PROFILE 배포"
#nohup java -Xms128m -Xmx4096m -jar -Dspring.profiles.active=$IDLE_PROFILE $IDLE_APPLICATION_PATH > /dev/null &
echo "nohup java -jar -Dspring.profiles.active=$IDLE_PROFILE $IDLE_APPLICATION_PATH > /dev/null &"
nohup java -jar -Dspring.profiles.active=$IDLE_PROFILE $IDLE_APPLICATION_PATH > /dev/null &

echo "> $IDLE_PROFILE 어플리케이션 시작"

echo "> $IDLE_PROFILE 10초 후 Health check 시작"
echo "> curl -s http://localhost:$IDLE_PORT/actuator/health "
sleep 10

for retry_count in {1..10}
do
  response=$(curl -s http://localhost:$IDLE_PORT/actuator/health)
  up_count=$(echo $response | grep 'UP' | wc -l)

  if [ $up_count -ge 1 ]
  then # $up_count >= 1 ("UP" 문자열이 있는지 검증)
      echo "> Health check: ${response}"
      echo "> Health check 성공"
      break
  else
      echo "> Health check의 응답을 알 수 없거나 혹은 status가 UP이 아닙니다."
      echo "> Health check: ${response}"
  fi

  if [ $retry_count -eq 10 ]
  then
    echo "> Health check 실패. "
    echo "> Nginx에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..."
  sleep 10
done

echo "> 스위칭"
sleep 10

echo "Set Profile : $IDLE_PROFILE"
echo "> 전환할 Port: $IDLE_PORT"
echo "> Port 전환"
echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" |sudo tee /etc/nginx/conf.d/service-url.inc

echo "> Nginx Reload"
sudo service nginx restart
sleep 5

PROFILE=$(curl -s http://localhost/process-env)
echo "> Nginx Current Proxy Port: $PROFILE"

echo "> 기존 Demdon Kill"
IDLE_APPLICATION_KILL=$IDLE_PROFILE_KILL-springboot-webservice.jar

echo "> IDLE_APPLICATION_KILL 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID_KILL=$(pgrep -f $IDLE_APPLICATION_KILL)

if [ -z $IDLE_PID_KILL ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> sudo kill -15 $IDLE_PID_KILL"
  sudo kill -15 $IDLE_PID_KILL
  sleep 5
fi
