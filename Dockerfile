# Stage 1: Build the Spring Boot app with Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Use a lightweight JDK image to run the jar
FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/target/pt-website-backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
