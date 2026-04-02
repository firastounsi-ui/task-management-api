# Task Management API

A backend REST API for managing users and tasks, built with Java, Spring Boot, PostgreSQL, Flyway, and Docker.

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Flyway
- Docker
- Gradle

## Features

- CRUD operations for users and tasks
- DTO-based API design
- Validation with meaningful error responses
- Global exception handling
- Flyway database migrations
- PostgreSQL running in Docker

## Run the Project

Start PostgreSQL:

```bash
docker run -d \
  --name task-postgres \
  -p 5434:5432 \
  -e POSTGRES_DB=taskdb \
  -e POSTGRES_USER=taskuser \
  -e POSTGRES_PASSWORD=taskpass \
  postgres:15-alpine
```

Run the app:

```bash
./gradlew bootRun
```

## API Endpoints

### Users
- POST /api/users
- GET /api/users
- GET /api/users/{id}
- PUT /api/users/{id}
- DELETE /api/users/{id}

### Tasks
- POST /api/tasks
- GET /api/tasks
- GET /api/tasks/{id}
- PUT /api/tasks/{id}
- DELETE /api/tasks/{id}

## What I learned

- Designing REST APIs using Spring Boot
- Structuring applications with Controller, Service, and Repository layers
- Using PostgreSQL with Docker for local development
- Managing database schema with Flyway migrations
- Implementing DTOs to separate API and persistence layers
- Applying validation and handling errors with global exception handling
