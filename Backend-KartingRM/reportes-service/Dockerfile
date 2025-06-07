FROM openjdk:11
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} reportes-service-1.jar
ENTRYPOINT ["java","-jar","/reportes-service-1.jar"]