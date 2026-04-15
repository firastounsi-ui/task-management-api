# 🚀 Task Management API
![Tests](https://img.shields.io/badge/tests-passing-brightgreen)

A production-leaning backend API for managing users and tasks, built with Spring Boot and secured using JWT-based authentication and role-based authorization.

This project demonstrates how to design and implement a secure, stateless REST API with clean architecture, proper validation, and testing practices.

---

## 🛠 Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Security**
- **PostgreSQL**
- **Flyway (Database Migration)**
- **Docker**
- **JUnit & Mockito (Unit Testing)**
- **MockMvc (Integration Testing)**
- **Gradle**

---

## ✨ Key Features

- CRUD operations for tasks and basic user management
- JWT-based authentication (stateless)
- Role-based authorization (**USER / ADMIN**)
- Secure password hashing with BCrypt
- DTO-based API design (clear separation of concerns)
- Global exception handling with structured JSON responses
- Database versioning with Flyway
- Dockerized PostgreSQL setup

---

## 🔐 Security

The application uses **stateless JWT authentication**:

- Users authenticate via `/api/auth/login`
- A JWT token is issued and must be included in requests
- Token contains user identity (**email**) and role
- A custom `JwtAuthenticationFilter` validates tokens on each request

### Authorization

- Role-based access control enforced via Spring Security
- Example:
  - `DELETE /api/users/**` → **ADMIN only**
  - Unauthorized access returns structured **403 responses**

### Error Handling

- Custom JSON responses for:
  - `401 Unauthorized` (missing/invalid token)
  - `403 Forbidden` (insufficient permissions)

---

## 🧪 Testing

The project includes both **unit and integration tests**:

### Unit Tests
- Implemented with **JUnit + Mockito**
- Focus on service layer business logic
- Covers success and edge cases (e.g. duplicate users, missing data)

### Integration Tests
- Implemented with **MockMvc**
- Tests full request flow:
  - Register → Login → Authenticated requests
  - Unauthorized access (401)
  - Forbidden access (403)
  - Role-based endpoint protection

- Integration tests run against a local PostgreSQL instance with Flyway migrations

👉 Tests verify not only functionality, but also security behavior.

---

## 🏗 Architecture

The application follows a **layered architecture**:

```

Controller → Service → Repository → Database

````

Key design choices:

- **DTO pattern** to separate API and persistence layers
- **Stateless authentication** for scalability
- **Security isolation** (JWT service, filter, config separated)
- **Flyway migrations** for controlled schema evolution

---

## ⚙️ Setup & Run

### 1. Start PostgreSQL (Docker)

```bash
docker run -d \
  --name task-postgres \
  -p 5434:5432 \
  -e POSTGRES_DB=taskdb \
  -e POSTGRES_USER=taskuser \
  -e POSTGRES_PASSWORD=taskpass \
  postgres:15-alpine
````

### 2. Run the Application

```bash
./gradlew bootRun
```

App runs at:

```
http://localhost:8080
```

---

## 📡 API Overview

### Authentication

* `POST /api/auth/register`
* `POST /api/auth/login`

### Users

* `GET /api/users`
* `GET /api/users/{id}`
* `DELETE /api/users/{id}` (ADMIN only)

### Tasks

* `POST /api/tasks`
* `GET /api/tasks`
* `PUT /api/tasks/{id}`
* `DELETE /api/tasks/{id}`

### Authorization Header

---

Authorization: Bearer <your-jwt-token>
---

All protected endpoints require a valid JWT token.

---

## 🧠 Engineering Highlights

* Designed a **stateless authentication system** using JWT
* Implemented **role-based access control** with Spring Security
* Structured application with **clear separation of concerns**
* Ensured reliability through **unit + integration testing**
* Used **Flyway** for safe and reproducible database migrations
* Built with a focus on **real-world backend practices**, not just functionality

---

## 🎯 Summary

This project demonstrates how to build a secure, testable, and maintainable backend system using Spring Boot, following modern backend engineering practices.

---

## 👨‍💻 Author

* GitHub: [https://github.com/firastounsi-ui](https://github.com/firastounsi-ui)  

