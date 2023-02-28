FROM alpine/git:2.36.3 AS git
WORKDIR /app/src
RUN git clone https://github.com/rbrtjhs/eventmanager.git

FROM maven:3.8-eclipse-temurin-17-alpine AS mvn
COPY --from=git /app/src /app/src
WORKDIR /app/src/eventmanager/microservice/event-manager-parent
RUN mvn -DskipTests -ntp  -am -pl ./event/event-query-service clean install

FROM eclipse-temurin:17-alpine
COPY --from=mvn /root/.m2 /root/.m2
RUN mkdir /app
COPY --from=mvn /app/src/eventmanager/microservice/event-manager-parent/event/event-query-service/target/event-query-service-1.0.0.jar /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/event-query-service-1.0.0.jar"]