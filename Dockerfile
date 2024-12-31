FROM openjdk:11-jre-slim-buster
EXPOSE 8080

WORKDIR /application/data_handler/
COPY target/*.jar /work/data_handler.jar
CMD ["java", "-jar", "data_handler.jar"]