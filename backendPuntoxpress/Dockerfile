# Build stage: Compilación y pruebas
FROM openjdk:17-jdk-slim AS builder
WORKDIR /app

# Copia el código fuente y las configuraciones necesarias
COPY . .

# Compila y ejecuta pruebas
RUN ./gradlew clean build test --no-daemon

# Runtime stage: Imagen ligera con OpenJDK
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia el archivo JAR generado desde la etapa de compilación
COPY --from=builder /app/build/libs/*.jar app.jar

# Exponer el puerto en el que escucha la aplicación
EXPOSE 8080

# Configuración de entrada para ejecutar el archivo JAR
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
