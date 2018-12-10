#!/bin/bash

#for i in {1..10}
#do
#   RAND=`openssl rand -base64 12`
#   RAND2=`openssl rand -base64 3`
#   DATA="{\"serialNo\": \"AC$RAND2-$i\", \"firmwareVersion\": \"$RAND\"}"
#   echo $DATA
#   curl -X POST localhost:8080/ac -H "Content-Type: application/json" -d "$DATA"
#done

SERIALNO="AC2waS-9"

for i in {1..500}
do
   RAND=`openssl rand -base64 12`
   RAND2=`openssl rand -base64 3`
   TEMP=$(($RANDOM % 50))
   HU=$(($RANDOM % 100))
   MON=$(($RANDOM % 11))
   STATUS="ok"
   DATA="{\"serialNo\": \"$SERIALNO\", \"temperature\": \"$TEMP\", \"humidity\": \"$HU\", \"carbonMonoxide\":\"$MON\", \"healthStatus\":\"$STATUS\"}"
   echo $DATA
   curl -X POST localhost:8080/ac/$SERIALNO/sensorReading -H "Content-Type: application/json" -d "[$DATA]"
done
