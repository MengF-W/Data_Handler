FROM openjdk:11-jre-slim-buster
ENV elasticsearch.ip=host.docker.internal
ENV mqtt.ip=host.docker.internal
EXPOSE 8080

WORKDIR /application/data_handler/
COPY target/*.jar /application/data_handler/data_handler.jar
CMD ["java", "-jar", "/application/data_handler/data_handler.jar"]