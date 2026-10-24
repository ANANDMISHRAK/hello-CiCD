# ---------- Stage 1: build the executable JAR ----------
# Use an image that already contains Maven and Java 17 for compiling the app.
FROM maven:3.9-eclipse-temurin-17 AS build
# Set a predictable working directory inside the temporary build container.
WORKDIR /workspace
# Copy Maven configuration first; this can help Docker cache dependency layers.
COPY pom.xml .
# Copy application and test source files into the build container.
COPY src ./src
# Copy Checkstyle configuration because Maven verify may read it during a full build.
COPY config ./config
# Compile/package the app; tests are run in CI separately, so this Docker build skips them.
RUN mvn --batch-mode --no-transfer-progress -DskipTests package

# ---------- Stage 2: runtime image ----------
# Use a smaller Java runtime image without Maven build tools.
FROM eclipse-temurin:17-jre
# Set the directory where the application JAR will live.
WORKDIR /app
# Create a system user with a fixed ID so the service does not need to run as root.
RUN useradd --system --uid 10001 spring
# Copy only the executable JAR from the build stage, not Maven or source code.
COPY --from=build /workspace/target/hello-cicd-0.0.1-SNAPSHOT.jar app.jar
# Run the application as the unprivileged user created above.
USER 10001
# Documents that the application listens on port 8080; this does not publish the port by itself.
EXPOSE 8080
# Starts Java as the container's main process and uses a memory percentage suitable for containers.
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]
