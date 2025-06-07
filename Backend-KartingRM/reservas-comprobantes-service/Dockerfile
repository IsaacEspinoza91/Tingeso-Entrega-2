FROM openjdk:11
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} reservas-comprobantes-service-1.jar
ENTRYPOINT ["java","-jar","/reservas-comprobantes-service-1.jar"]