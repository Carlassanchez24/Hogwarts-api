# Usar una imagen oficial
FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

# Copiar el archivo JAR generado por Maven
COPY target/hogwarts-api-0.0.1-SNAPSHOT.jar hogwarts-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "hogwarts-api.jar"]