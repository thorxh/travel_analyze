#! /bin/bash

cd `dirname $0`
cd ..
BASE_HOME=`pwd`

JAVA_SERVER='travel-analyze'

JAVA=`which java`
ln -sf ${JAVA} ${BASE_HOME}/tmp/${JAVA_SERVER}

nohup ${BASE_HOME}/tmp/${JAVA_SERVER} -jar ${BASE_HOME}/libs/travel-analyze-0.0.1-SNAPSHOT-jar-with-dependencies.jar
    ${BASE_HOME}/result \
    ${BASE_HOME}/config \
    > ${BASE_HOME}/logs/travel.log 2>&1 &

tail -f ${BASE_HOME}/logs/travel.log