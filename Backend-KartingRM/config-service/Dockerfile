FROM openjdk:11
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} config-service-1.jar
ENTRYPOINT ["java","-jar","/config-service-1.jar"]