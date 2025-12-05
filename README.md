# Sistema de Nómina - Aplicación Monolítica

## Descripción del Proyecto

Sistema de gestión de nómina. Esta aplicación monolítica proporciona funcionalidades completas para el registro de empleados, autenticación, cálculo de nóminas y administración del sistema.

**Versión:** 1.0-SNAPSHOT  
**Grupo:** mx.uaemex.fi  
**Artefacto:** mx-uaemex-isii-monolitic

---

## Tecnologías Utilizadas

### Lenguaje de Programación
- **Java 17** - Lenguaje principal de desarrollo

### Frameworks Principales
- **Spring Boot 3.5.6** - Framework base de la aplicación
  - Spring Boot Starter Web - Desarrollo de APIs REST
  - Spring Boot Starter Data JPA - Persistencia de datos
  - Spring Boot Starter Security - Seguridad y autenticación
  - Spring Boot Starter Thymeleaf - Motor de plantillas
  - Spring Boot Starter Validation - Validación de datos
  - Spring Boot Starter Actuator - Monitoreo y métricas

### Seguridad
- **Spring Security** - Gestión de autenticación y autorización
- **JJWT (JSON Web Tokens) 0.13.0** - Generación y validación de tokens JWT
  - jjwt-api
  - jjwt-impl
  - jjwt-jackson

### Base de Datos
- **H2 Database** - Base de datos en memoria para desarrollo y pruebas

### Herramientas de Desarrollo
- **Lombok** - Reducción de código boilerplate
- **Spring Boot DevTools** - Herramientas de desarrollo y recarga automática
- **Maven** - Gestión de dependencias y construcción del proyecto

### Testing
- **Spring Boot Starter Test** - Suite de pruebas unitarias
- **Spring Security Test** - Pruebas de seguridad
- **JUnit 5** - Framework de pruebas (incluido en Spring Boot Starter Test)
- **Mockito** - Framework de mocking (incluido en Spring Boot Starter Test)
- **JaCoCo 0.8.11** - Análisis de cobertura de código

### Herramientas de Construcción
- **Maven Compiler Plugin** - Compilación del proyecto
- **Maven Surefire Plugin 3.2.5** - Ejecución de pruebas
- **Spring Boot Maven Plugin** - Empaquetado de la aplicación

---

## Arquitectura

### Arquitectura General
El proyecto implementa una **arquitectura monolítica en capas** basada en el patrón **MVC (Modelo-Vista-Controlador)** con una estructura modular y organizada.

### Capas de la Aplicación

```
┌─────────────────────────────────────────────────┐
│           CAPA DE PRESENTACIÓN                  │
│  (Controllers + DTOs + Views/Thymeleaf)         │
├─────────────────────────────────────────────────┤
│           CAPA DE SEGURIDAD                     │
│  (Filters + Security Config + JWT)              │
├─────────────────────────────────────────────────┤
│           CAPA DE NEGOCIO                       │
│  (Services + Validation)                        │
├─────────────────────────────────────────────────┤
│           CAPA DE PERSISTENCIA                  │
│  (Repositories + Models/Entities)               │
├─────────────────────────────────────────────────┤
│           CAPA DE BASE DE DATOS                 │
│  (H2 Database)                                  │
└─────────────────────────────────────────────────┘
```

### Estructura del Código

```
src/main/java/mx/uaemex/fi/
├── api/
│   ├── controller/      # Controladores REST y MVC
│   ├── dto/            # Objetos de transferencia de datos
│   ├── exception/      # Excepciones personalizadas
│   ├── filter/         # Filtros de seguridad (JWT)
│   ├── model/          # Entidades del dominio
│   ├── repository/     # Repositorios JPA
│   ├── service/        # Lógica de negocio
│   └── validation/     # Validadores personalizados
├── config/             # Configuración de Spring Security y beans
└── Main.java           # Punto de entrada de la aplicación
```

### Componentes Principales

#### 1. **Capa de Presentación**
- **Controladores**: Gestión de endpoints REST y vistas
- **DTOs**: Objetos para transferencia de datos entre capas
- **Vistas**: Plantillas Thymeleaf para interfaz web

#### 2. **Capa de Seguridad**
- **JwtAuthenticationFilter**: Filtro para validación de tokens JWT
- **Security Config**: Configuración de Spring Security
- **UserDetailsService**: Servicio de autenticación de usuarios

#### 3. **Capa de Negocio**
- **AuthService**: Lógica de autenticación y registro
- **EmpleadoService**: Gestión de empleados
- **NominaService**: Cálculo y gestión de nóminas
- **JwtService**: Generación y validación de tokens
- **Validadores Personalizados**: Validación de RFC, email, períodos, etc.

#### 4. **Capa de Persistencia**
- **Repositories**: Interfaces JPA para acceso a datos
- **Modelos**: Entidades JPA (Empleado, Nomina, Acceso)

### Patrones de Diseño Utilizados
- **MVC (Model-View-Controller)**: Separación de responsabilidades
- **DTO (Data Transfer Object)**: Transferencia de datos entre capas
- **Repository Pattern**: Abstracción del acceso a datos
- **Dependency Injection**: Inversión de control con Spring
- **Filter Pattern**: Filtros para procesamiento de peticiones
- **Builder Pattern**: Construcción de objetos complejos (Lombok @Builder)

---

## Pruebas

El proyecto cuenta con una **cobertura de pruebas excepcional** con 221 pruebas unitarias distribuidas en 25 archivos de prueba.

### Estadísticas de Pruebas

| Métrica                        | Valor        |
|--------------------------------|--------------|
| **Total de Pruebas**           | 221          |
| **Pruebas Exitosas**           | 221 (100%)   |
| **Tiempo de Ejecución**        | ~24 segundos |
| **Cobertura de Instrucciones** | **97%** ✅    |
| **Cobertura de Ramas**         | **81%** ✅    |
| **Cobertura de Líneas**        | **98%** ✅    |
| **Cobertura de Métodos**       | **98%** ✅    |
| **Cobertura de Clases**        | **100%** ✅   |

### Distribución por Capa

- **Controladores**: 2 archivos, 60 pruebas (98% cobertura)
- **Servicios**: 7 archivos, 83 pruebas (100% cobertura)
- **Validadores**: 5 archivos, 27 pruebas (100% cobertura)
- **Filtros**: 1 archivo, 11 pruebas (82% cobertura)
- **DTOs**: 5 archivos, 10 pruebas (100% cobertura)
- **Modelos**: 3 archivos, 15 pruebas
- **Excepciones**: 2 archivos, 6 pruebas (100% cobertura)

### Ejecutar Pruebas

```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar pruebas con reporte de cobertura JaCoCo
mvn clean test jacoco:report

# Ver reporte de cobertura
# El reporte se genera en: target/site/jacoco/index.html
```

**Para más detalles sobre las pruebas, consulta [RESUMEN-DE-PRUEBAS.md](docs/RESUMEN-DE-PRUEBAS.md)**

---

## Guía de Inicio Rápido

### Requisitos Previos

- **Java 17** o superior
- **Maven 3.6+**
- **Docker** (opcional, para despliegue en contenedores)

### 1. Clonar el Repositorio

```bash
git clone <url-del-repositorio>
cd mx-uaemex-isii-monolitic
```

### 2. Compilar el Proyecto

```bash
# Compilar sin ejecutar pruebas
mvn clean compile

# Compilar y ejecutar pruebas
mvn clean install

# Compilar y empaquetar (genera JAR)
mvn clean package
```

### 3. Ejecutar la Aplicación

#### Opción A: Usando Maven

```bash
# Ejecutar en modo desarrollo
mvn spring-boot:run

# Ejecutar con perfil específico
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Opción B: Ejecutar el JAR generado

```bash
# Compilar y empaquetar
mvn clean package

# Ejecutar el JAR
java -jar target/mx-uaemex-isii-monolitic-1.0-SNAPSHOT.jar

# Ejecutar con perfil específico
java -jar target/mx-uaemex-isii-monolitic-1.0-SNAPSHOT.jar --spring.profiles.active=dev
```

### 4. Verificar la Aplicación

Una vez iniciada la aplicación, estará disponible en:

```
http://localhost:8080
```

#### Endpoints de Actuator

```
http://localhost:8080/actuator
http://localhost:8080/actuator/health
```

---

## Despliegue con Docker

### Construcción de la Imagen

```bash
# Construir la imagen Docker
docker build -t uaemex-nomina:latest .
```

### Ejecutar el Contenedor

#### Opción 1: Con variables de entorno en línea de comandos

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

#### Opción 2: Usando archivo .env

1. Crear archivo `.env` con las variables de entorno necesarias
2. Ejecutar el contenedor:

```bash
docker run -d -p 8080:8080 \
  --env-file .env \
  --name uaemex-nomina-app \
  uaemex-nomina:latest
```

### Comandos Útiles de Docker

```bash
# Ver contenedores en ejecución
docker ps

# Ver logs de la aplicación
docker logs uaemex-nomina-app

# Detener el contenedor
docker stop uaemex-nomina-app

# Iniciar el contenedor
docker start uaemex-nomina-app

# Eliminar el contenedor
docker rm uaemex-nomina-app

# Eliminar la imagen
docker rmi uaemex-nomina:latest
```

**Para una guía completa de Docker, consulta [README-DOCKER.md](docs/README-DOCKER.md)**

---

## Configuración

### Perfiles de Spring

La aplicación soporta múltiples perfiles:

- **dev**: Perfil de desarrollo
- **prod**: Perfil de producción
- **test**: Perfil para pruebas

### Variables de Entorno

| Variable                 | Descripción                       | Ejemplo              |
|--------------------------|-----------------------------------|----------------------|
| `SPRING_PROFILES_ACTIVE` | Perfil activo de Spring           | `dev`, `prod`        |
| `JWT_SECRET`             | Secreto para firma de JWT         | `tu-secreto-seguro`  |
| `JWT_EXPIRATION_MS`      | Tiempo de expiración del JWT (ms) | `86400000` (24h)     |
| `DB_URL`                 | URL de conexión a base de datos   | `jdbc:h2:mem:testdb` |
| `DB_USERNAME`            | Usuario de base de datos          | `sa`                 |
| `DB_PASSWORD`            | Contraseña de base de datos       | ``                   |

---

## Autores

Este proyecto fue desarrollado para la clase de Ingeniería de Software II impartida en la Facultad de Ingeniería de la Universidad Autónoma del Estado de México por:

| Nombre corto (nombre + apellido) | Correo institucional            | Correo personal          |
|----------------------------------|---------------------------------|--------------------------|
| Fernando Espinosa                | jfespinosas001@alumno.uaemex.mx | jferespinosa18@gmail.com |



---

## Documentación Adicional

- [RESUMEN-DE-PRUEBAS.md](docs/RESUMEN-DE-PRUEBAS.md) - Documentación completa de pruebas y cobertura
- [README-DOCKER.md](docs/README-DOCKER.md) - Guía completa de despliegue con Docker

---

## Soporte

Para reportar problemas o solicitar nuevas características, contacta al equipo de desarrollo.

---

**Última actualización:** Diciembre 2025

