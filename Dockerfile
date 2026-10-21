FROM adoptopenjdk/openjdk11:latest
EXPOSE 500

WORKDIR /application/data_handler/
COPY target/*.jar /application/data_handler/data_handler.jar
CMD ["java","-Xmx510m","-Xms256M","-XX:PermSize=256m","-XX:MaxPermSize=510m","-Xss510M", "-jar", "/application/data_handler/data_handler.jar"]