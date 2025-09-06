# Use official OpenJDK with Maven
FROM maven:3.9.9-eclipse-temurin-22 AS build

# Stage 1: Build
FROM openjdk:22-jdk-slim AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Run
FROM openjdk:22-jdk-slim
WORKDIR /app
COPY --from=build /app/target/ecommerce-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
