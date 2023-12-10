# Use the official Maven image with a specific version as the build image
FROM maven:3.8.1-openjdk-8 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project file
COPY pom.xml .

# Download and cache dependencies
RUN mvn dependency:go-offline -B

# Copy the project source
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use the official OpenJDK base image with a specific version
FROM openjdk:8-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file built by Maven from the build image
COPY --from=build /app/target/spring-boot-docker.jar .

# Expose the port that the application will run on
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "spring-boot-docker.jar"]
