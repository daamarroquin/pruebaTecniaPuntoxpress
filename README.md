# Despliegue con Docker Compose

## Requisitos Previos

1. **Docker**:
   - Asegúrate de que Docker esté instalado y funcionando correctamente.
   - Verifica la instalación con:
     ```bash
     docker --version
     ```

2. **Docker Compose**:
   - Asegúrate de tener Docker Compose instalado.
   - Verifica la instalación con:
     ```bash
     docker-compose --version
     ```

3. **Estructura del Proyecto**:
   El proyecto debe tener la siguiente estructura:
   ```plaintext
   .
   ├── backendPuntoxpress/
   │   ├── Dockerfile
   │   ├── build.gradle
   │   ├── src/
   │   └── ...
   ├── frontendPuntoxpress/
   │   ├── Dockerfile
   │   ├── package.json
   │   ├── angular.json
   │   └── ...
   ├── docker-compose.yml
   └── README.md
Instrucciones para Desplegar
Construir y Levantar los Servicios: Ejecuta el siguiente comando en el directorio donde se encuentra el archivo docker-compose.yml:

```bash
docker-compose up --build
```
Esto hará lo siguiente:

Construirá las imágenes para el backend y el frontend.
Levantará los contenedores db, backend, y frontend.
Verificar los Servicios: Una vez que los servicios estén levantados, verifica:

Base de datos PostgreSQL: localhost:5432
Backend: localhost:8081
Frontend: localhost:4200
Logs de los Contenedores: Puedes verificar los logs de cada contenedor con:

```bash
docker-compose logs <nombre_del_servicio>
```
Ejemplo:

bash

```bash
docker-compose logs backend
```
Apagar los Servicios
Para apagar y eliminar los contenedores, ejecuta:


```bash
docker-compose down
```
Esto detendrá y eliminará todos los contenedores y redes creados por Docker Compose.

Problemas Comunes y Soluciones
El backend no puede conectarse a la base de datos:

Asegúrate de que las variables de entorno SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME, y SPRING_DATASOURCE_PASSWORD en el servicio backend están configuradas correctamente.
El frontend no puede comunicarse con el backend:

Asegúrate de que la configuración del archivo nginx.conf o de tu aplicación Angular apunte al nombre del servicio puntoxpressBackend y al puerto 8081.
Cambios en el Código no Reflejados:

Si realizas cambios en el código, reconstruye las imágenes:
```bash
docker-compose up --build
```
