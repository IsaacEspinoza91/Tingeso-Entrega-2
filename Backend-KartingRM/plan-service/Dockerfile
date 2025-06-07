FROM openjdk:11
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} plan-service-1.jar
ENTRYPOINT ["java","-jar","/plan-service-1.jar"]