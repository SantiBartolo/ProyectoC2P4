- ✅ Validaciones de datos
- ✅ Manejo de excepciones personalizado
- ✅ Generación de certificados en PDF (BONUS)
- ✅ Pruebas unitarias con JUnit 5 y Mockito
- ✅ Arquitectura MVC limpia
- ✅ Uso de Stream API de Java
- ✅ DTOs para separación de capas
- ✅ Interfaz web con Thymeleaf para gestionar estudiantes sin necesidad de Postman

## 🛠️ Tecnologías

- **Java 17+**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **SQLite**
- **OpenPDF** (para generación de PDFs)
- **ModelMapper** (para mapeo DTOs)
- **JUnit 5** y **Mockito** (para pruebas)

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/universidad/proyecto/
│   │   ├── controller/      # Controladores REST
│   │   ├── service/         # Lógica de negocio
│   │   ├── repository/      # Repositorios JPA
│   │   ├── model/           # Entidades JPA
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── exception/       # Excepciones personalizadas
│   │   ├── util/            # Utilidades (PDF, constantes)
│   │   └── config/          # Configuraciones
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/universidad/proyecto/
        ├── service/         # Pruebas de servicio
        └── controller/      # Pruebas de controlador
```

## 🚀 Cómo Ejecutar

### Prerrequisitos

- Java 17 o superior
- Maven 3.6+

### Pasos

1. **Clonar el repositorio** (o navegar al directorio del proyecto)

2. **Compilar / ejecutar (con wrapper incluido)**
   ```bash
   # Linux / macOS
   ./mvnw clean verify
   ./mvnw spring-boot:run

   # Windows
   .\mvnw.cmd clean verify
   .\mvnw.cmd spring-boot:run
   ```

   O ejecutar directamente el jar:
   ```bash
   java -jar target/proyecto-1.0.0.jar
   ```

3. **Verificar que está corriendo**
   - API REST: `http://localhost:8080/api/students`
   - Interfaz web: `http://localhost:8080/students`
   - Base de datos SQLite: se creará automáticamente como `students.db` en el directorio raíz

### Interfaz web (Thymeleaf)

1. Levanta la app (`./mvnw spring-boot:run`).
2. Abre `http://localhost:8080/students`.
3. Allí puedes:
   - Crear estudiantes con el botón **“+ Nuevo Estudiante”**.
   - Editar o eliminar registros desde la tabla.
   - Descargar el certificado PDF desde el botón **“PDF”**.
   - Ir directamente al endpoint REST para ver la respuesta JSON con **“Ver API (JSON)”**.

## 📡 Endpoints REST

### Base URL
```
http://localhost:8080/api/students
```

### 1. Crear Estudiante
**POST** `/api/students`

**Request Body:**
```json
{
  "firstName": "Ana",
  "lastName": "Gomez",
  "email": "ana.gomez@email.com",
  "dateOfBirth": "2003-05-12",
  "program": "Ingeniería de Sistemas",
  "documentNumber": "123456789"
}
```

**Ejemplo con curl:**
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d "{\"firstName\":\"Ana\",\"lastName\":\"Gomez\",\"email\":\"ana.gomez@email.com\",\"dateOfBirth\":\"2003-05-12\",\"program\":\"Ingeniería de Sistemas\",\"documentNumber\":\"123456789\"}"
```

**Response (201 Created):**
```json
{
  "id": 1,
  "firstName": "Ana",
  "lastName": "Gomez",
  "email": "ana.gomez@email.com",
  "dateOfBirth": "2003-05-12",
  "program": "Ingeniería de Sistemas",
  "documentNumber": "123456789"
}
```

### 2. Listar Todos los Estudiantes
**GET** `/api/students`

**Ejemplo con curl:**
```bash
curl -X GET http://localhost:8080/api/students
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "firstName": "Ana",
    "lastName": "Gomez",
    "email": "ana.gomez@email.com",
    "dateOfBirth": "2003-05-12",
    "program": "Ingeniería de Sistemas",
    "documentNumber": "123456789"
  }
]
```

### 3. Obtener Estudiante por ID
**GET** `/api/students/{id}`

**Ejemplo con curl:**
```bash
curl -X GET http://localhost:8080/api/students/1
```

**Response (200 OK):**
```json
{
  "id": 1,
  "firstName": "Ana",
  "lastName": "Gomez",
  "email": "ana.gomez@email.com",
  "dateOfBirth": "2003-05-12",
  "program": "Ingeniería de Sistemas",
  "documentNumber": "123456789"
}
```

### 4. Actualizar Estudiante
**PUT** `/api/students/{id}`

**Request Body:**
```json
{
  "firstName": "Ana Maria",
  "lastName": "Gomez",
  "email": "ana.gomez@email.com",
  "dateOfBirth": "2003-05-12",
  "program": "Ingeniería de Sistemas",
  "documentNumber": "123456789"
}
```

**Ejemplo con curl:**
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d "{\"firstName\":\"Ana Maria\",\"lastName\":\"Gomez\",\"email\":\"ana.gomez@email.com\",\"dateOfBirth\":\"2003-05-12\",\"program\":\"Ingeniería de Sistemas\",\"documentNumber\":\"123456789\"}"
```

**Response (200 OK):**
```json
{
  "id": 1,
  "firstName": "Ana Maria",
  "lastName": "Gomez",
  "email": "ana.gomez@email.com",
  "dateOfBirth": "2003-05-12",
  "program": "Ingeniería de Sistemas",
  "documentNumber": "123456789"
}
```

### 5. Eliminar Estudiante
**DELETE** `/api/students/{id}`

**Ejemplo con curl:**
```bash
curl -X DELETE http://localhost:8080/api/students/1
```

**Response (204 No Content)**

### 6. Generar Certificado PDF (BONUS)
**POST** `/api/students/{id}/certificate`

**Ejemplo con curl:**
```bash
curl -X POST http://localhost:8080/api/students/1/certificate \
  -H "Accept: application/pdf" \
  --output certificado.pdf
```

**Response (200 OK):** Archivo PDF descargado

## 🧪 Ejecutar Pruebas

Para ejecutar todas las pruebas unitarias:

```bash
mvn test
```

Para ejecutar pruebas específicas:

```bash
mvn test -Dtest=StudentServiceTest
mvn test -Dtest=StudentControllerTest
```

## 📊 Modelo de Datos

### Entidad Student

```java
Student {
    Long id;
    String firstName;        // Obligatorio, 2-50 caracteres
    String lastName;         // Obligatorio, 2-50 caracteres
    String email;           // Obligatorio, formato email válido, único
    LocalDate dateOfBirth;  // Obligatorio
    String program;         // Opcional, máximo 100 caracteres
    String documentNumber;  // Opcional, máximo 20 caracteres
}
```

### Tabla SQL (SQLite)

```sql
CREATE TABLE students (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    date_of_birth TEXT NOT NULL,
    program TEXT,
    document_number TEXT
);
```

## ⚠️ Manejo de Errores

La aplicación maneja errores con respuestas estructuradas:

**Ejemplo de error (404 Not Found):**
```json
{
  "timestamp": "2025-11-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Estudiante no encontrado con id: 999",
  "path": "/api/students/999"
}
```

**Ejemplo de error de validación (400 Bad Request):**
```json
{
  "timestamp": "2025-11-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Errores de validación: {firstName=El nombre es obligatorio}",
  "path": "/api/students"
}
```

## 🔧 Configuración

El archivo `application.properties` contiene:

- Puerto del servidor: `8080`
- Base de datos SQLite: `students.db`
- Configuración JPA/Hibernate
- Logging

## 📝 Validaciones

- **firstName**: Obligatorio, 2-50 caracteres
- **lastName**: Obligatorio, 2-50 caracteres
- **email**: Obligatorio, formato válido, único en la base de datos
- **dateOfBirth**: Obligatorio, formato LocalDate
- **program**: Opcional, máximo 100 caracteres
- **documentNumber**: Opcional, máximo 20 caracteres

## 🎯 Características Adicionales (BONUS)

### ✅ Generación de PDF
- Endpoint para generar certificados en PDF
- Usa OpenPDF (librería open source)
- Incluye información completa del estudiante

### ✅ Persistencia SQLite
- Base de datos SQLite embebida
- Se crea automáticamente al iniciar la aplicación
- Archivo: `students.db` en el directorio raíz

### ✅ Stream API
- Filtrado de estudiantes por programa
- Ordenamiento por apellido
- Implementado en `StudentService`

## 📚 Arquitectura

El proyecto sigue el patrón **MVC (Modelo-Vista-Controlador)**:

- **Model**: Entidades JPA (`Student`)
- **View**: DTOs de respuesta (`StudentResponseDTO`)
- **Controller**: Controladores REST (`StudentController`)
- **Service**: Lógica de negocio (`StudentService`)
- **Repository**: Acceso a datos (`StudentRepository`)

## 🌿 Ramas de Git

El proyecto debe organizarse en tres ramas principales:

- **develop**: Desarrollo principal
- **test**: Para pruebas e integraciones
- **production**: Versión estable

## 👥 Contribuciones

Cada integrante debe indicar su contribución en el README.

## 📄 Licencia

Este proyecto es parte de un trabajo académico.

## 📞 Contacto

**Nombre:** Santiago Bartolo Sansón  
**Correo:** sa.bartolo22@ciaf.edu.co  
**Carrera:** Ingeniería en Desarrollo de Software — 5º semestre  
**GitHub:** SantiBartolo


**Desarrollado para Programación 4 - Universidad La CIAF**

**Desarrollado para Programación 4 - Universidad**

