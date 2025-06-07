FROM openjdk:11
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} rack-semanal-service-1.jar
ENTRYPOINT ["java","-jar","/rack-semanal-service-1.jar"]