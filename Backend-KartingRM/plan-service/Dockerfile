FROM openjdk:11
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} plan-service.jar
ENTRYPOINT ["java","-jar","/plan-service.jar"]