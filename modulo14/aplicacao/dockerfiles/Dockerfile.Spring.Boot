# Use an official Maven image to build the application
FROM maven:3.6.3-openjdk-11-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and the source code to the container
COPY backend/pom.xml .
COPY backend/src ./src

# Package the application to create the JAR file
RUN mvn clean package -DskipTests

# Use a smaller base image for the final image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the final image
COPY --from=build /app/target/*.jar /app/springbootapp.jar

# Expose port 8080
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "/app/springbootapp.jar"]