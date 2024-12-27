# Stage 1: Build the project with Maven
FROM maven:3.8.5-eclipse-temurin-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download dependencies (caching layer)
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copy the source code and build the project
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Use a lightweight JRE image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the Maven build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8088

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]