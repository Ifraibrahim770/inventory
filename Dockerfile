# AS <NAME> to name this stage as maven
FROM maven:3.8.4 AS maven

ENV SPRING_RABBITMQ_HOST=localhost
ENV SPRING_RABBITMQ_PORT=5672
ENV SPRING_RABBITMQ_USERNAME=guest
ENV SPRING_RABBITMQ_PASSWORD=guest
ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/inventory
ENV SPRING_DATASOURCE_USERNAME=mysqluser
ENV SPRING_DATASOURCE_PASSWORD=ThePassword
ENV SPRING_DATASOURCE_DRIVER-CLASS-NAME=com.mysql.cj.jdbc.Driver
ENV REDIS_HOST=localhost
ENV REDIS_PORT=6379
ENV REDIS_PASSWORD=
ENV DEFAULT_TTL=180



WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package -Dmaven.test.skip

# Java 17
FROM openjdk:17-jdk-slim


ARG JAR_FILE=inventory-manager-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app


COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/
EXPOSE 8080

ENTRYPOINT ["java","-jar","inventory-manager-0.0.1-SNAPSHOT.jar"]






