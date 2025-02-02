# Set up a base image
FROM openjdk:19-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the pre-built JAR
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Expose the applications's default port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
