# 👤 Microservicio Usuarios GG

Microservicio de gestión de usuarios para la plataforma de videojuegos **Monsoon**. Expone una API REST con soporte de **HATEOAS**, autenticación basada en **JWT** y documentación interactiva con **Swagger / OpenAPI**.

## 🛠 Tecnologías

- Java 17
- Spring Boot 3.x (Spring Web, Spring Data JPA, Spring Security)
- Spring HATEOAS
- JWT (io.jsonwebtoken / JJWT)
- Springdoc OpenAPI (Swagger UI)
- MySQL 8.0
- Lombok
- Maven
- Docker / Docker Compose

## 📂 Arquitectura

```
controller   → recibe las peticiones HTTP
service      → lógica de negocio
repository   → acceso a datos (Spring Data JPA)
model        → entidad Usuario
DTO          → objetos de transferencia expuestos por la API
assembler    → construye los DTO y agrega enlaces HATEOAS
security     → filtro JWT, utilidades de token y configuración de Spring Security
config       → configuración de Swagger / OpenAPI
```

## 🧾 Modelo

```java
private Long id;
private String nombre;
private String email;
private String nombreUsuario;
private String contraseña;
private String rol;
```

> ⚠️ La entidad `Usuario` incluye la contraseña, pero **nunca se expone** en las respuestas de la API: el endpoint siempre devuelve `UsuarioGGDTO`, que no contiene ese campo.

## ⚙️ Configuración

### Opción A — Ejecución local

Crea la base de datos antes de ejecutar:

```sql
CREATE DATABASE usuariogg_db;
```

`application.properties`:

```properties
spring.application.name=usuariogg
server.port=8082

spring.datasource.url=jdbc:mysql://localhost:3306/usuariogg_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> Si usas Laragon, cambia `password=` por `password=root`.

### Opción B — Docker Compose (recomendado)

El proyecto incluye `Dockerfile` y `docker-compose.yml`, que levantan la app **y** la base de datos MySQL juntas.

```bash
docker compose up --build
```

Esto expone:

| Servicio | Puerto host | Puerto interno |
|---|---|---|
| App Spring Boot | `8082` | `8080` |
| MySQL | `3307` | `3306` |

La base de datos `usuariogg_db` y las credenciales se configuran automáticamente vía variables de entorno en `docker-compose.yml` (usuario `root`, password `root`).

## ▶️ Cómo ejecutar

**Local con Maven:**

```bash
mvn spring-boot:run
```

**Con Docker:**

```bash
docker compose up --build
```

La API quedará disponible en `http://localhost:8082` (Docker) o `http://localhost:8082` / el puerto definido en `application.properties` (local).

## 🔐 Autenticación (JWT)

La API usa tokens **Bearer JWT**. Las rutas bajo `/auth/**` y los recursos de Swagger son públicos; el resto de los endpoints requieren autenticación.

### Login

```
POST http://localhost:8082/auth/login
```

**Body:**

```json
{
  "nombreUsuario": "juan123",
  "contraseña": "1234"
}
```

**Respuesta:**

```json
{
  "status": "Exitoso",
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Usa el token recibido en el header de las siguientes peticiones:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

> ⚠️ Esta versión solo usa `nombreUsuario` para generar el token; el campo `contraseña` se acepta en el body pero **no se valida** contra la base de datos. Pensado como base para integrar autenticación completa.

## 📌 Endpoints

Todos bajo el prefijo `/api/v0/usuarios` (requieren JWT, salvo se indique lo contrario).

| Método | URL | Descripción |
|---|---|---|
| GET | `/api/v0/usuarios` | Obtener todos los usuarios |
| GET | `/api/v0/usuarios/{id}` | Obtener usuario por ID |
| POST | `/api/v0/usuarios` | Crear usuario |
| PUT | `/api/v0/usuarios/{id}` | Actualizar usuario |
| DELETE | `/api/v0/usuarios/{id}` | Eliminar usuario |
| POST | `/auth/login` | Autenticarse y obtener token JWT (público) |

### Ejemplo POST `/api/v0/usuarios`

```json
{
  "nombre": "Juan",
  "email": "juan@gmail.com",
  "contraseña": "1234",
  "nombreUsuario": "juan123",
  "rol": "USER"
}
```

### Ejemplo GET por ID

```
GET http://localhost:8082/api/v0/usuarios/1
```

### Ejemplo de respuesta (con HATEOAS)

```json
{
  "id": 1,
  "nombre": "Juan",
  "email": "juan@gmail.com",
  "nombreUsuario": "juan123",
  "rol": "USER",
  "_links": {
    "self": { "href": "http://localhost:8082/api/v0/usuarios/1" },
    "usuarios": { "href": "http://localhost:8082/api/v0/usuarios" }
  }
}
```

## 📖 Documentación interactiva (Swagger)

Una vez levantado el proyecto:

```
http://localhost:8082/swagger-ui/index.html
```

Permite probar los endpoints directamente desde el navegador, incluyendo autenticación con el token JWT (botón **Authorize**).

## 🧪 Tests

El proyecto incluye pruebas con `MockMvc` sobre la capa de controlador (`ControladorUsuarioTest`), que validan:

- Obtención de un usuario por ID, incluyendo los enlaces `_links.self` y `_links.usuarios`.
- Creación de usuario, validando el código `201 Created` y los datos retornados.

```bash
mvn test
```

## 📁 Estructura del proyecto

```
src/main/java/com/example/usuariogg/
├── assembler/      # UsuarioGGAssembler (HATEOAS)
├── config/         # Configuración de Swagger/OpenAPI
├── controller/     # authcontrolador, controladorusuario
├── DTO/            # UsuarioGGDTO
├── model/          # Usuario
├── repository/      # UsuarioRepository
├── security/       # JWTFILTRO, JWTUTIL, SEGURIDADmoonsoon
└── service/        # serviciousuario
```

## 🚧 Notas / mejoras pendientes

- Cifrar la contraseña antes de persistirla (por ejemplo, con BCrypt).
- Validar credenciales reales en `/auth/login` contra la base de datos.
- Usar una clave JWT fija y persistida de forma segura (actualmente se genera en memoria al iniciar la app).
