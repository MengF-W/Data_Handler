# Overall Idea

![image](https://github.com/user-attachments/assets/d0e82d89-c5e1-4dbc-b3e3-d2fce6574387)

The idea is to monitor and analsyt MQTT enabled devices by collect messages and feed into data pipeline

# Data_Handler Description
The data handler application process and save the MQTT enabled devices data into database. This application acts as a data collector and provide sources to the data pipeline application.

# Libraries used
* Spring Boot
* Spring Data Elastic Search
* Spring Data JPA
* PostgreSQL JDBC Driver
* Spring REST Controller
* Eclipse Paho Java MQTT
* Spring Boot Test

# Packaging Command
`mvn clean package` - To clean and create JAR

# Docker Image Command
`docker build -t data_handler .`    -To build the docker image

# Docker Container Command
`docker-compose up -d`      -To start the docker container from the docker image with the docker compose file configuration  

# Sending Payload
`mosquitto_pub -t /device/receive -m "{"name": "TestDevice","deviceType":"TestDeviceType","messageContent":"TestMessageContent"}"` -To send MQTT payload through command

# Query Documents from Database
`curl -X GET "localhost:9200/devices/_search?pretty" -H 'Content-Type:application/json'`      -To retrieve MQTT payload from database. 

# Example of Document Returned
![image](https://github.com/user-attachments/assets/d833388a-c648-47b7-b41e-dfa24c58f916)

# Publish
It has been published as a service and can be accessed from [Data Panel](https://github.com/MengF-W/Data_Panel)

# MQTT Broker Server Option
* Public Server : Public MQTT Broker Server such as [Mosquitto](https://test.mosquitto.org/) is available and it is free to test. Clients can read or publish to public topics. Therefore, it is not safe or private for sensitive data
* Private Host Server : The MQTT Broker can be deployed into cloud service provider such as [Render](https://render.com/deploy-docker/eclipse-mosquitto). After testing find that there is message packet size limitation (for about 5000 bytes length) in Render host service and the client receiver's connection will disconnect when receive message. The max_packet_size setting does not affect it. The explanation from Render about the connection can drop due to: <br />

-Instance lifecycle events<br />
	Render periodically replaces instances during deploys or maintenance. When an instance shuts down, all its WebSocket connections close — even mid-transfer.<br />
<br />
-Network interruptions<br />
	Outbound IP routing changes can reset TCP connections to external resources. From your service's perspective, this looks like any transient network issue.<br />

* Cloud's Managed MQTT Platform : Cloud's Managed MQTT Platform provider such as [EMQX](https://www.emqx.com/en/cloud) that has Free Tier usage service with limited quota
