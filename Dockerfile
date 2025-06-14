# Elemento de ambientacion para correr la app en la imagen.
# OPENJDK 17 -> tomando en cuenta la version minima requerida en el POM.
FROM openjdk:17.0.2

# directorio de trabajo interno de la imagen
WORKDIR /app

# indica que va a copiar el JAR generado en el target al directorio de trabajo (.)
COPY ./target/ms-springboot-crud-api-0.0.1-SNAPSHOT.jar /app

# puerto que usara el contenedor para escuchar las peticiones.
EXPOSE 8001

# comandos de ejecucion de la app en el contenedor
ENTRYPOINT ["java", "-jar", "ms-springboot-crud-api-0.0.1-SNAPSHOT.jar"]