FROM openjdk:11
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} descuento-grupo-service-1.jar
ENTRYPOINT ["java","-jar","/descuento-grupo-service-1.jar"]