# Build stage
FROM amazoncorretto:21-alpine-jdk AS build
WORKDIR /workspace/app

# Copiar el ejecutable de Maven y darle permisos de ejecución
COPY mvnw .
COPY .mvn .mvn

# Copiar el archivo pom.xml
COPY pom.xml .

# Copiar el código fuente
COPY src src

# Dar permisos de ejecución al archivo mvnw
RUN chmod +x mvnw

# Ejecutar Maven para empaquetar la aplicación
RUN ./mvnw package -DskipTests

# Run stage
FROM amazoncorretto:21-alpine-jdk
WORKDIR /app

# Copiar el artefacto generado por el primer contenedor (la etapa de construcción)
COPY --from=build /workspace/app/target/hogwarts-api-0.0.1-SNAPSHOT.jar /app/hogwarts-api.jar

# Exponer el puerto en el que la aplicación va a correr
EXPOSE 8080

# Ejecutar el archivo JAR
CMD ["java", "-jar", "hogwarts-api.jar"]
