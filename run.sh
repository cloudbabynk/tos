#!/bin/bash
pkill -9 java
nohup  /home/toptea/jdk1.8.0_111/bin/java -Duser.timezone=CST -jar  /home/toptea/*.jar&
