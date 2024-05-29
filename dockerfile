FROM maven:latest AS builder

WORKDIR /app

COPY . .

RUN mvn clean package

#2nd Stage
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/lnd-springboot.jar /app/lnd-springboot.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/lnd-springboot.jar"]