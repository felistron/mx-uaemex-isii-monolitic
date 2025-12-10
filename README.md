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

## Guía de Inicio Rápido

### Requisitos Previos

- **Java 17** o superior
- **Maven 3.6+**
- **Docker** (opcional, para despliegue en contenedores)

### 1. Clonar el Repositorio

```bash
git clone https://github.com/felistron/mx-uaemex-isii-monolitic.git
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

## Arquitectura

### Arquitectura General
El proyecto implementa una **arquitectura monolítica en capas** con una clara **separación de responsabilidades** entre la presentación, lógica de negocio y persistencia de datos. Esta estructura facilita el mantenimiento, testing y posible evolución hacia una arquitectura de microservicios.

### Capas de la Aplicación

```
┌─────────────────────────────────────────────────────────┐
│         CAPA DE PRESENTACIÓN (presentation/)            │
│  • Controladores REST/MVC                               │
│  • DTOs (Data Transfer Objects)                         │
│  • Filtros de seguridad (JWT)                           │
│  • Vistas Thymeleaf                                     │
├─────────────────────────────────────────────────────────┤
│         CAPA DE LÓGICA DE NEGOCIO (logic/)              │
│  • Servicios de negocio                                 │
│  • Validadores personalizados                           │
│  • Excepciones del dominio                              │
│  • Reglas de negocio y cálculos                         │
├─────────────────────────────────────────────────────────┤
│         CAPA DE PERSISTENCIA (persistence/)             │
│  • Entidades JPA (Models)                               │
│  • Repositorios Spring Data JPA                         │
│  • Mapeo objeto-relacional                              │
├─────────────────────────────────────────────────────────┤
│         CONFIGURACIÓN (config/)                         │
│  • Spring Security Configuration                        │
│  • Bean Definitions                                     │
├─────────────────────────────────────────────────────────┤
│         BASE DE DATOS                                   │
│  • H2 Database (en memoria)                             │
└─────────────────────────────────────────────────────────┘
```

### Principios de Diseño Aplicados

- **Separación de Responsabilidades (SoC)**: Cada capa tiene responsabilidades bien definidas
- **Bajo Acoplamiento**: Las capas interactúan a través de interfaces bien definidas
- **Alta Cohesión**: Los componentes relacionados están agrupados en la misma capa
- **Inversión de Dependencias**: Las capas superiores dependen de abstracciones, no de implementaciones

### Estructura del Código

La aplicación está organizada en tres capas principales que reflejan una clara separación de responsabilidades:

```
src/main/java/mx/uaemex/fi/
├── presentation/          # CAPA DE PRESENTACIÓN
│   ├── controller/        # Controladores REST y MVC
│   ├── dto/              # Objetos de transferencia de datos
│   └── filter/           # Filtros de seguridad (JWT)
│
├── logic/                # CAPA DE LÓGICA DE NEGOCIO
│   ├── service/          # Servicios de negocio
│   ├── validation/       # Validadores personalizados
│   └── exception/        # Excepciones personalizadas
│
├── persistence/          # CAPA DE PERSISTENCIA
│   ├── model/            # Entidades del dominio (JPA)
│   └── repository/       # Repositorios JPA
│
├── config/               # Configuración de Spring Security y beans
└── Main.java             # Punto de entrada de la aplicación
```

### Componentes Principales

#### 1. **Capa de Presentación** (`presentation/`)
Responsable de la interacción con el usuario y la exposición de servicios:
- **Controladores** (`controller/`): Gestión de endpoints REST y vistas web
- **DTOs** (`dto/`): Objetos para transferencia de datos entre capas
- **Filtros** (`filter/`): Filtros de seguridad y procesamiento de peticiones (JWT)

#### 2. **Capa de Lógica de Negocio** (`logic/`)
Contiene toda la lógica de negocio y reglas de la aplicación:
- **Servicios** (`service/`):
  - **AuthService**: Lógica de autenticación y registro
  - **EmpleadoService**: Gestión de empleados
  - **NominaService**: Cálculo y gestión de nóminas
  - **JwtService**: Generación y validación de tokens JWT
  - **CustomUserDetailsService**: Servicio de autenticación de usuarios
- **Validadores** (`validation/`): Validación de RFC, email, períodos, contraseñas, etc.
- **Excepciones** (`exception/`): Excepciones personalizadas del dominio

#### 3. **Capa de Persistencia** (`persistence/`)
Gestiona el acceso y persistencia de datos:
- **Modelos** (`model/`): Entidades JPA del dominio (Empleado, Nomina, Acceso)
- **Repositorios** (`repository/`): Interfaces JPA para acceso a datos

#### 4. **Configuración** (`config/`)
- **Security Config**: Configuración de Spring Security
- **Bean Definitions**: Definición de beans del contenedor Spring

### Patrones de Diseño Utilizados
- **Arquitectura en Capas (Layered Architecture)**: Separación clara entre presentación, lógica y persistencia
- **MVC (Model-View-Controller)**: Patrón para la capa de presentación
- **DTO (Data Transfer Object)**: Transferencia de datos entre capas
- **Repository Pattern**: Abstracción del acceso a datos en la capa de persistencia
- **Service Layer Pattern**: Encapsulación de la lógica de negocio
- **Dependency Injection**: Inversión de control con Spring IoC
- **Filter Pattern**: Filtros para procesamiento de peticiones HTTP
- **Builder Pattern**: Construcción de objetos complejos (Lombok @Builder)
- **Singleton Pattern**: Gestión de beans Spring (por defecto)

### Diagramas UML

El proyecto incluye **6 diagramas UML completos** en formato PlantUML que documentan toda la arquitectura:

**Diagramas de Estructura (6):**
1. **Diagrama Entidad-Relación** - Modelo de datos del sistema
2. **Diagrama de Clases - Capa de Persistencia** - Entidades JPA y Repositorios
3. **Diagrama de Clases - Capa de Servicios** - Service Layer y lógica de negocio
4. **Diagrama de Clases - Capa de Lógica de Negocio** - Validadores y Excepciones
5. **Diagrama de Clases - Capa de Presentación** - Controladores, DTOs y Filtros
6. **Diagrama de Despliegue** - Arquitectura de infraestructura y despliegue

**📊 Accede a los diagramas en:** [docs/uml/](docs/uml/)

Los diagramas pueden visualizarse usando:
- PlantUML Online Server: https://www.plantuml.com/plantuml/uml/
- Extensión PlantUML para VS Code o IntelliJ IDEA

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

## Autores

Este proyecto fue desarrollado para la clase de Ingeniería de Software II impartida en la Facultad de Ingeniería de la Universidad Autónoma del Estado de México por:

| Nombre corto (nombre + apellido) | Correo institucional            | Correo personal          |
|----------------------------------|---------------------------------|--------------------------|
| Fernando Espinosa                | jfespinosas001@alumno.uaemex.mx | jferespinosa18@gmail.com |



---

## Documentación Adicional

### Documentación para Usuarios
- **[📘 MANUAL-DE-USUARIO.md](docs/MANUAL-DE-USUARIO.md)** - Manual completo para personal de Recursos Humanos
  - Guía paso a paso de todas las funcionalidades
  - Ejemplos prácticos y casos de uso
  - Solución de problemas comunes
  - Preguntas frecuentes
  - Glosario de términos

### Documentación Técnica
- **[📊 Diagramas UML](docs/uml/README.md)** - 9 diagramas completos de arquitectura en PlantUML
  - **Diagramas de Estructura (6):**
    - Diagrama Entidad-Relación
    - Diagrama de Clases - Capa de Persistencia
    - Diagrama de Clases - Capa de Servicios
    - Diagrama de Clases - Capa de Lógica de Negocio
    - Diagrama de Clases - Capa de Presentación
    - Diagrama de Despliegue

### Documentación de Calidad
- **[RESUMEN-DE-PRUEBAS.md](docs/RESUMEN-DE-PRUEBAS.md)** - Documentación completa de pruebas y cobertura

### Documentación de Despliegue
- **[README-DOCKER.md](docs/README-DOCKER.md)** - Guía completa de despliegue con Docker

---

## Soporte

Para reportar problemas o solicitar nuevas características, contacta al equipo de desarrollo.

---

**Última actualización:** Diciembre 2025

