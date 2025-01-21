# Construcción del frontend
FROM node:16 AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build -- --configuration production

# Servidor Node.js
FROM node:16
WORKDIR /app
COPY --from=builder /app/dist/frontend-puntoxpress /app
RUN npm install -g serve

# Exponer el puerto estándar para aplicaciones web
EXPOSE 4200

# Ejecutar el servidor con 'serve'
CMD ["serve", "-s", ".", "-l", "4200"]
