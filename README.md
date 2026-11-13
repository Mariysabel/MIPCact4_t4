# CRUD Rápido - API REST con Spring Boot

## Descripción

Proyecto desarrollado con **Spring Boot** que implementa una API REST para la administración de alumnos, incorporando operaciones CRUD, conexión a una base de datos MySQL, autenticación mediante JWT y protección de endpoints con Spring Security.

También se implementaron DTO para controlar la información intercambiada entre el cliente y el servidor, validaciones de datos de entrada y manejo centralizado de excepciones.

## Tecnologías utilizadas

- **Java 25:** lenguaje de programación.
- **Spring Boot 4.1.2:** framework para el desarrollo de la aplicación.
- **Spring Web MVC:** creación de endpoints REST.
- **Spring Data JPA:** persistencia y comunicación con la base de datos.
- **MySQL:** sistema gestor de base de datos.
- **Spring Security:** autenticación y protección de endpoints.
- **JWT:** generación y validación de tokens de autenticación.
- **Hibernate Validator:** validación de datos mediante Bean Validation.
- **Lombok:** reducción de código repetitivo.
- **Maven:** administración de dependencias y construcción del proyecto.

## Funcionalidades implementadas

### 1. CRUD de alumnos

Se implementaron las operaciones necesarias para administrar alumnos:

| Método HTTP | Endpoint | Descripción |
|---|---|---|
| GET | `/api/alumnos` | Obtener todos los alumnos. |
| GET | `/api/alumnos/{id}` | Obtener un alumno por su ID. |
| POST | `/api/alumnos` | Registrar un nuevo alumno. |
| PUT | `/api/alumnos/{id}` | Actualizar un alumno. |
| DELETE | `/api/alumnos/{id}` | Eliminar un alumno. |

### 2. Autenticación con JWT

Se implementó un sistema de autenticación que permite registrar usuarios e iniciar sesión mediante correo electrónico y contraseña.

| Método HTTP | Endpoint | Descripción |
|---|---|---|
| POST | `/api/auth/register` | Registrar un usuario. |
| POST | `/api/auth/login` | Iniciar sesión y obtener un JWT. |

Las contraseñas se almacenan utilizando BCrypt, evitando guardar contraseñas en texto plano.

El token JWT se utiliza para autenticar las solicitudes hacia los endpoints protegidos.

Para acceder a ellos se debe enviar el siguiente encabezado HTTP:

```http
Authorization: Bearer <token>
```

### 3. Seguridad con Spring Security

Se configuró Spring Security para proteger los endpoints de la API.

- Las rutas `/api/auth/**` son públicas.
- Las demás rutas requieren autenticación.
- Se implementó un filtro personalizado para validar los JWT.
- Se configuró la aplicación para trabajar sin sesiones, utilizando autenticación stateless.
- Las solicitudes sin un token válido reciben una respuesta `401 Unauthorized`.

### 4. Uso de DTO

Se implementaron Data Transfer Objects (DTO) para controlar la información que recibe y devuelve la API.

Se utilizaron los siguientes DTO:

- `AlumnoRequestDTO`: recibe el nombre, edad y correo del alumno.
- `AlumnoResponseDTO`: devuelve el ID, nombre y edad del alumno.
- `RegisterRequestDTO`: recibe el correo y contraseña para registrar usuarios.
- `LoginRequestDTO`: recibe las credenciales para iniciar sesión.
- `AuthResponseDTO`: estructura la respuesta de autenticación.

De esta manera, se evita exponer directamente las entidades de la base de datos en las respuestas de la API.

### 5. Validación de datos

Se implementaron validaciones mediante `@Valid` y anotaciones de Bean Validation.

Algunas de las validaciones utilizadas son:

- `@NotBlank`: evita campos vacíos.
- `@Email`: verifica el formato del correo electrónico.
- `@Size`: establece límites en la longitud de las contraseñas.
- `@Min`: establece una edad mínima para los alumnos.

Cuando los datos no cumplen las condiciones establecidas, la API devuelve una respuesta `400 Bad Request` con un mensaje descriptivo.

### 6. Manejo de excepciones y códigos HTTP

Se implementó un manejador global de excepciones mediante `@RestControllerAdvice`, centralizando el tratamiento de errores.

| Código | Descripción |
|---|---|
| 200 | Solicitud procesada correctamente. |
| 201 | Recurso creado correctamente. |
| 204 | Recurso eliminado correctamente. |
| 400 | Datos de entrada inválidos. |
| 401 | Usuario no autenticado o credenciales incorrectas. |
| 404 | Recurso no encontrado. |
| 409 | Conflicto, por ejemplo, correo electrónico duplicado. |

## Estructura del proyecto

La aplicación está organizada por paquetes para separar las responsabilidades de cada componente.

```text
src/
└── main/
    ├── java/com/acti4/crudrapido/
    │   ├── config/
    │   │   └── SecurityConfig.java
    │   ├── controller/
    │   │   ├── AlumnoController.java
    │   │   └── AuthController.java
    │   ├── dto/
    │   │   ├── AlumnoRequestDTO.java
    │   │   ├── AlumnoResponseDTO.java
    │   │   ├── RegisterRequestDTO.java
    │   │   ├── LoginRequestDTO.java
    │   │   └── AuthResponseDTO.java
    │   ├── entity/
    │   │   ├── Alumno.java
    │   │   └── Usuario.java
    │   ├── exception/
    │   │   ├── GlobalExceptionHandler.java
    │   │   ├── ResourceNotFoundException.java
    │   │   ├── EmailAlreadyExistsException.java
    │   │   └── InvalidCredentialsException.java
    │   ├── filter/
    │   │   └── JwtAuthenticationFilter.java
    │   ├── repository/
    │   │   ├── AlumnoRepository.java
    │   │   └── UsuarioRepository.java
    │   ├── service/
    │   │   ├── AlumnoService.java
    │   │   ├── AuthService.java
    │   │   ├── JwtService.java
    │   │   └── CustomUserDetailsService.java
    │   └── CrudrapidoApplication.java
    └── resources/
        └── application.properties
```

*Se muestran los principales componentes de la aplicación.*

## Configuración del proyecto

### Requisitos previos

Antes de ejecutar el proyecto, es necesario contar con:

- JDK 25.
- MySQL instalado y funcionando.
- Una base de datos creada en MySQL.
- Git para clonar el repositorio.

### 1. Clonar el repositorio

```bash
git clone URL_DEL_REPOSITORIO
```

Entrar a la carpeta del proyecto:

```bash
cd crudrapido
```

### 2. Crear el archivo application.properties

**IMPORTANTE:** El archivo `application.properties` está incluido en `.gitignore`, ya que contiene información privada, como las credenciales de conexión a la base de datos y la clave secreta utilizada para JWT.

Por esta razón, el archivo no se encuentra disponible al clonar el repositorio y debe crearse manualmente.

Su ubicación es:

```text
src/main/resources/application.properties
```

Agregar la siguiente configuración, sustituyendo los valores de ejemplo por los datos del entorno:

```properties
# Configuración del servidor
server.port=8088

# Configuración de MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/NOMBRE_BASE_DATOS
spring.datasource.username=USUARIO_MYSQL
spring.datasource.password=CONTRASENA_MYSQL

# Configuración JWT
jwt.secret=CLAVE_SECRETA_BASE64
jwt.expiration=86400000
```

**Consideraciones:**

- `NOMBRE_BASE_DATOS`: nombre de la base de datos creada en MySQL.
- `USUARIO_MYSQL`: usuario utilizado para conectarse a MySQL.
- `CONTRASENA_MYSQL`: contraseña correspondiente a dicho usuario.
- `CLAVE_SECRETA_BASE64`: clave secreta codificada en Base64, generada de forma segura con al menos 32 bytes aleatorios antes de codificarla.

No se deben compartir ni subir las credenciales reales al repositorio.

### 3. Construir y ejecutar el proyecto

Desde la carpeta raíz del proyecto, ejecutar en Windows PowerShell:

```powershell
.\mvnw.cmd clean install
```

Para iniciar la aplicación:

```powershell
.\mvnw.cmd spring-boot:run
```

La aplicación estará disponible en:

```text
http://localhost:8088
```

## Pruebas de la API

Las solicitudes de la API pueden probarse utilizando herramientas como Postman o Bruno.

### 1. Registro de usuario

**Método:** `POST`

**Endpoint:** `/api/auth/register`

Se envían las credenciales para registrar un nuevo usuario.

**Captura de pantalla:**

![Registro de usuario](docs/capturas/01-registro-usuario.png)

### 2. Inicio de sesión

**Método:** `POST`

**Endpoint:** `/api/auth/login`

Se envían las credenciales del usuario registrado. Si son correctas, la API devuelve un JWT que permite acceder a los endpoints protegidos.

**Captura de pantalla:**

![Inicio de sesión y generación de JWT](docs/capturas/02-login-jwt.png)

### 3. Consultar todos los alumnos

**Método:** `GET`

**Endpoint:** `/api/alumnos`

Devuelve la lista de alumnos registrados en la base de datos.

**Captura de pantalla:**

![Consultar alumnos](docs/capturas/03-get-alumnos.png)

### 4. Consultar alumno por ID

**Método:** `GET`

**Endpoint:** `/api/alumnos/{id}`

Permite consultar la información de un alumno específico mediante su identificador.

**Captura de pantalla:**

![Consultar alumno por ID](docs/capturas/04-get-alumno-id.png)

### 5. Registrar alumno

**Método:** `POST`

**Endpoint:** `/api/alumnos`

Permite registrar un nuevo alumno enviando su nombre, edad y correo electrónico.

**Captura de pantalla:**

![Registrar alumno](docs/capturas/05-post-alumno.png)

### 6. Actualizar alumno

**Método:** `PUT`

**Endpoint:** `/api/alumnos/{id}`

Permite modificar la información de un alumno existente.

**Captura de pantalla:**

![Actualizar alumno](docs/capturas/06-put-alumno.png)

### 7. Eliminar alumno

**Método:** `DELETE`

**Endpoint:** `/api/alumnos/{id}`

Permite eliminar un alumno mediante su identificador.

**Captura de pantalla:**

![Eliminar alumno](docs/capturas/07-delete-alumno.png)

### 8. Validación de acceso mediante JWT

Para comprobar la protección de los endpoints, se realiza una solicitud a una ruta protegida sin enviar el token de autenticación.

La API devuelve `401 Unauthorized`, indicando que se requiere autenticación.

**Captura de pantalla:**

![Acceso sin token](docs/capturas/08-acceso-sin-token.png)


## Autor
María Isabel Pérez Cruz

Proyecto desarrollado como práctica de desarrollo de API REST utilizando Spring Boot, MySQL y Spring Security.