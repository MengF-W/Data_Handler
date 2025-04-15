# Overall Idea

![Capture](https://github.com/user-attachments/assets/31773b7d-9c17-445f-b22b-d5a0c1da345d)
The idea is to monitor and analsyt MQTT enabled devices by collect messages and feed into data pipeline

# Data_Handler Description
The data handler application process and save the MQTT enabled devices data into database. This application acts as a data collector and provide sources to the data pipeline application.

# Libraries used
* Spring Boot
* Spring Data Elastic Search
* Spring REST Controller
* Eclipse Paho Java MQTT
* Spring Boot Test

# Packaging Command
`mvn clean package` - To clean and create JAR

# Docker Image Command
`docker build -t data_handler`    -To build the docker image

# Docker Container Command
`docker-compose up -d`      -To start the docker container from the docker image with the docker compose file configuration  

# Sending Payload
`mosquitto_pub -t /device/receive -m "{"name": "TestDevice","deviceType":"TestDeviceType","messageContent":"TestMessageContent"}"` -To send MQTT payload through command

# Query Documents from Database
`curl -X GET "localhost:9200/devices/_search?pretty" -H 'Content-Type:application/json'`      -To retrieve MQTT payload from database. 

# Example of Document Returned
![image](https://github.com/user-attachments/assets/d833388a-c648-47b7-b41e-dfa24c58f916)
