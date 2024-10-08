# Stage 1: Using the JDK to copy the JAR
FROM eclipse-temurin:21-jdk-alpine AS builder
ARG JAR_FILE=target/api-rest-0.0.1-SNAPSHOT.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar

# Stage 2: Use a lighter JRE for runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
