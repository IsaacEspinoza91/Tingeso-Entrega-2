FROM openjdk:11
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} dias-especiales-service-1.jar
ENTRYPOINT ["java","-jar","/dias-especiales-service-1.jar"]