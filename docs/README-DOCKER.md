# Guía de Despliegue con Docker

## Construcción y Ejecución

### 1. Construir la imagen Docker

```bash
docker build -t uaemex-nomina:latest .
```

### 2. Ejecutar el contenedor

#### Opción A: Pasando variables por línea de comandos

```bash
docker run -d -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  -e JWT_SECRET=tu-secreto-jwt-super-seguro-de-al-menos-256-bits \
  -e JWT_EXPIRATION_MS=86400000 \
  -e DB_URL=jdbc:h2:mem:testdb \
  -e DB_USERNAME=sa \
  -e DB_PASSWORD= \
  --name uaemex-nomina-app \
  uaemex-nomina:latest
```

#### Opción B: Usando archivo .env

1. Crear archivo `.env`:

2. Editar `.env` con tus valores

3. Ejecutar el contenedor:
   ```bash
   docker run -d -p 8080:8080 \
     --env-file .env \
     --name uaemex-nomina-app \
     uaemex-nomina:latest
   ```

### 3. Verificar que el contenedor está corriendo

```bash
docker ps
```

### 4. Ver los logs de la aplicación

```bash
docker logs uaemex-nomina-app
```

### 5. Acceder a la aplicación

La aplicación estará disponible en: http://localhost:8080

### 6. Detener y eliminar el contenedor

```bash
docker stop uaemex-nomina-app
docker rm uaemex-nomina-app
```

## Comandos PowerShell (Windows)

### Construcción
```powershell
docker build -t uaemex-nomina:latest .
```

### Ejecución con variables en línea (PowerShell)
```powershell
docker run -d -p 8080:8080 `
  -e SPRING_PROFILES_ACTIVE=dev `
  -e JWT_SECRET=tu-secreto-jwt-super-seguro-de-al-menos-256-bits `
  -e JWT_EXPIRATION_MS=86400000 `
  -e DB_URL=jdbc:h2:mem:testdb `
  -e DB_USERNAME=sa `
  -e DB_PASSWORD= `
  --name uaemex-nomina-app `
  uaemex-nomina:latest
```

### Ejecución con archivo .env (PowerShell)
```powershell
docker run -d -p 8080:8080 `
  --env-file .env `
  --name uaemex-nomina-app `
  uaemex-nomina:latest
```

## Notas

- El Dockerfile utiliza un build multi-etapa para optimizar el tamaño de la imagen final
- La base de datos H2 se ejecuta en memoria, los datos se perderán al detener el contenedor
- El puerto 8080 del contenedor se mapea al puerto 8080 de tu máquina local
- Asegúrate de usar un JWT_SECRET seguro en producción

