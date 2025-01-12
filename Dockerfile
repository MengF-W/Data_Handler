FROM openjdk:11-jre-slim-buster
EXPOSE 500

WORKDIR /application/data_handler/
COPY target/*.jar /application/data_handler/data_handler.jar
CMD ["java", "-jar", "/application/data_handler/data_handler.jar"]