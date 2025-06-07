FROM openjdk:11
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} cliente-desc-frecu-service-1.jar
ENTRYPOINT ["java","-jar","/cliente-desc-frecu-service-1.jar"]