# Use a Maven base image to build the project
FROM maven:3.8.5-eclipse-temurin-17 as build
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml ./
COPY src ./src

# Build the Maven project
RUN mvn clean package -DskipTests

# Use a lightweight JRE image to run the application
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy the built JAR file from the Maven build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8888

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
