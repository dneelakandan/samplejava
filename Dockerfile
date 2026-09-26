# Stage 1: Build the Java application using Maven
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Minimal runtime image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/samplejava-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
