# Use official Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy Maven wrapper & pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (layer caching)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose application port
EXPOSE 8080

# Run the Spring Boot app
CMD ["java", "-jar", "target/cloudoptix-backend-0.0.1-SNAPSHOT.jar"]
