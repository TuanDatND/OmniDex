# =========================
# STAGE 1 — BUILD
# =========================
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copy Gradle wrapper & config first for better Docker cache
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Grant execute permission
RUN chmod +x gradlew

# Download dependencies (cached layer)
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY src src

# Build application
RUN ./gradlew bootJar -x test --no-daemon

# =========================
# STAGE 2 — RUNTIME
# =========================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring

# Copy built JAR
COPY --from=build /app/build/libs/*.jar app.jar

# Use non-root user
USER spring:spring

# JVM container optimizations
ENV JAVA_OPTS="\
-XX:MaxRAMPercentage=75.0 \
-Dfile.encoding=UTF-8 \
-Dspring.threads.virtual.enabled=true"

EXPOSE 8080

# Run application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]