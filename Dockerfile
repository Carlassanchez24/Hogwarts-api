# Usar una imagen oficial
FROM amazoncorretto:21-alpine-jdk


# Copiar el archivo JAR al contenedor

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
