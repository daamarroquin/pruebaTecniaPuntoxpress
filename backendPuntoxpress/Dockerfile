# Build stage: Compilación y pruebas
FROM openjdk:17-jdk-slim AS builder
WORKDIR /app

# Copia solo los archivos necesarios para la compilación
COPY gradlew gradlew.bat settings.gradle build.gradle /app/
COPY gradle /app/gradle
COPY src /app/src

# Dar permisos de ejecución al script gradlew
RUN chmod +x gradlew

# Compila y ejecuta pruebas
RUN ./gradlew clean build test --no-daemon

# Runtime stage: Imagen ligera con OpenJDK
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia el archivo JAR generado desde la etapa de compilación
COPY --from=builder /app/build/libs/*.jar app.jar

# Exponer el puerto en el que escucha la aplicación
EXPOSE 8081

# Configuración de entrada para ejecutar el archivo JAR
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
