FROM openjdk:11-jre-slim
COPY target/microservice.jar microservice.jar
ENTRYPOINT ["java", "-jar", "/microservice.jar"]