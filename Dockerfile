# Build stage
FROM amazoncorretto:21-alpine-jdk AS build
WORKDIR /workspace/app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Copy the project source
COPY src src

# Package the application
RUN ./mvnw package -DskipTests

# Run stage
FROM amazoncorretto:21-alpine-jdk
WORKDIR /app

# Copy the built artifact from the build stage
COPY --from=build /workspace/app/target/hogwarts-api-0.0.1-SNAPSHOT.jar /app/hogwarts-api.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "hogwarts-api.jar"]