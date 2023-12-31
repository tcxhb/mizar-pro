#!/bin/bash
#替换为你自己的jar包名称
APP_NAME=mizar-admin.jar
ENV=dev

#使用说明，用来提示输入参数
usage() {
    echo "Usage: sh 脚本名.sh [start|stop|restart|status]"
    exit 1
}

#检查程序是否在运行
is_exist(){
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}' `
  #如果不存在返回1，存在返回0
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

#启动方法
start(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is already running. pid=${pid} ."
  else
  	# jar包路径，日志文件路径
  	nohup java -jar $APP_NAME  --spring.profiles.active=$ENV >/dev/null 2>&1 &
    echo "应用启动成功"
  fi
}

#停止方法
stop(){
  is_exist
  if [ $? -eq "0" ]; then
    kill -15 $pid
     echo "应用kill -15 pid=${pid} ."
    sleep 10
    echo "应用已经停止pid=${pid} ."
  else
    echo "${APP_NAME} is not running"
  fi
}

#输出运行状态
status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running. Pid is ${pid}"
  else
    echo "${APP_NAME} is NOT running."
  fi
}

#重启
restart(){
  stop
  start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
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
