FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . /app
RUN mvn -Dmaven.test.skip package

FROM openjdk:17.0.1-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

CMD ["java", "-jar", "/app/app.jar"]
