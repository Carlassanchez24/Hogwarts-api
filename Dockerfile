FROM amazoncorretto:21-alpine-jdk

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo JAR generado por Maven al contenedor
COPY target/hogwarts-api-0.0.1-SNAPSHOT.jar /app/hogwarts-api.jar

# Exponer el puerto en el que la aplicación va a correr (8080 por defecto)
EXPOSE 8080

# Comando para ejecutar el archivo JAR
CMD ["java", "-jar", "hogwarts-api.jar"]
