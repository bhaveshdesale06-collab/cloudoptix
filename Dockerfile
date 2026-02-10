# ---------- BUILD STAGE ----------
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Maven wrapper + config FIRST
COPY .mvn .mvn
COPY mvnw mvnw
RUN chmod +x mvnw

# Copy pom.xml and download dependencies
COPY pom.xml pom.xml
RUN ./mvnw dependency:go-offline -B

# Copy source and build
COPY src src
RUN ./mvnw clean package -DskipTests

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
