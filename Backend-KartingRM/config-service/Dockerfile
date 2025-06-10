FROM openjdk:11
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} config-service.jar
ENTRYPOINT ["java","-jar","/config-service.jar"]