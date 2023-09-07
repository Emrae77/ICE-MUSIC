# Use a base image with Java 17 installed
# syntax=docker/dockerfile:1
FROM eclipse-temurin:17-jdk-jammy

RUN mkdir -p /usr/local/iceapi
ADD target/ICEMusic-0.0.1-SNAPSHOT.jar /usr/local/iceapi/

# Specify the command to run your Java application
CMD ["java", "-jar", "/usr/local/iceapi/ICEMusic-0.0.1-SNAPSHOT.jar"]