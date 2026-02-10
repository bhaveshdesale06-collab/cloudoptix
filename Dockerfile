# ---------- Build stage ----------
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copy Maven wrapper + config
COPY mvnw .
COPY .mvn .mvn

# ✅ Make mvnw executable (Linux requirement)
RUN chmod +x mvnw

# ✅ Copy pom.xml BEFORE running Maven
COPY pom.xml .

# Download dependencies (cacheable layer)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build application
RUN ./mvnw clean package -DskipTests


# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
