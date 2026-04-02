# 🚀 Task Management API

A RESTful backend API for managing users and tasks, built with **Java, Spring Boot, PostgreSQL, Flyway, and Docker**.

---

## 🛠 Tech Stack

- ☕ Java 17  
- 🌱 Spring Boot  
- 🗄 Spring Data JPA  
- 🐘 PostgreSQL  
- 🔄 Flyway (Database Migration)  
- 🐳 Docker  
- 📦 Gradle  

---

## ✨ Features

- Full CRUD operations for Users and Tasks  
- Clean layered architecture:
  - Controller
  - Service
  - Repository  
- DTO-based API design (separation of concerns)  
- Input validation with meaningful error messages  
- Global exception handling (`@RestControllerAdvice`)  
- Database versioning with Flyway  
- PostgreSQL running in Docker  

---

## 📁 Project Structure

```text
src/main/java/com/example/taskapi
├── controller
├── dto
├── entity
├── exception
├── repository
└── service
```

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
```

---

### 2. Run the application

```bash
./gradlew bootRun
```

App will start at:

```
http://localhost:8080
```

---

## 🧪 API Endpoints

### 👤 Users

| Method | Endpoint | Description |
|--------|---------|------------|
| POST   | `/api/users` | Create user |
| GET    | `/api/users` | Get all users |
| GET    | `/api/users/{id}` | Get user by ID |
| PUT    | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

---

### 📋 Tasks

| Method | Endpoint | Description |
|--------|---------|------------|
| POST   | `/api/tasks` | Create task |
| GET    | `/api/tasks` | Get all tasks |
| GET    | `/api/tasks/{id}` | Get task by ID |
| PUT    | `/api/tasks/{id}` | Update task |
| DELETE | `/api/tasks/{id}` | Delete task |

---

## 📌 Example Requests

### Create User

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Sara","email":"sara@example.com"}'
```

---

### Create Task

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title":"Backend Projekt final",
    "description":"DTOs und Update eingebaut",
    "status":"IN_PROGRESS",
    "userId":1
  }'
```

---

## ❗ Error Handling

- `404 Not Found` → Resource does not exist  
- `400 Bad Request` → Validation errors  

Example:

```json
{
  "title": "Title must not be blank",
  "userId": "User id must not be null"
}
```

---

## 📚 What I Learned

- Designing REST APIs with Spring Boot  
- Structuring applications using layered architecture  
- Using PostgreSQL with Docker for reproducible environments  
- Managing database schema with Flyway migrations  
- Separating API and persistence layers using DTOs  
- Implementing validation and global exception handling  

---

## 🎯 Summary

This project demonstrates how to build a clean, maintainable backend API using Spring Boot with proper architecture, validation, and database management.

---

## 👨‍💻 Author

- GitHub: https://github.com/firastounsi-ui
