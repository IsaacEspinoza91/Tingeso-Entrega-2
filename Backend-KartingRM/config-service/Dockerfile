FROM openjdk:11
ARG JAR_FILE=./target/config-service-1.jar
COPY ${JAR_FILE} config-service.jar
ENTRYPOINT ["java","-jar","/config-service.jar"]