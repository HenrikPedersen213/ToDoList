# Spring Boot To-Do List Application

A simple RESTful API for managing to-do items built with Spring Boot.

## Features

- Create, read, update, and delete to-do items
- Mark to-do items as completed
- Filter to-do items by completion status
- Search to-do items by title or description
- Validation for to-do item fields
- In-memory H2 database for development and testing
- Sample data loaded automatically for testing

## Technologies Used

- Java 17
- Spring Boot 3.5.0
- Spring Data JPA
- H2 Database
- Lombok
- Jakarta Validation

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Installation

1. Clone the repository:
   ```
   git clone <repository-url>
   ```

2. Navigate to the project directory:
   ```
   cd demo
   ```

3. Build the project:
   ```
   mvn clean install
   ```

4. Run the application:
   ```
   mvn spring-boot:run
   ```

The application will start on port 8080 by default.

## Database

The application uses an in-memory H2 database for development and testing. You can access the H2 console at:

```
http://localhost:8080/h2-console
```

Connection details:
- JDBC URL: `jdbc:h2:mem:tododb`
- Username: `sa`
- Password: `password`

## API Endpoints

### Get all to-do items
```
GET /api/todos
```

### Get to-do items by completion status
```
GET /api/todos?completed=true
```

### Search to-do items
```
GET /api/todos?search=keyword
```

### Get a to-do item by ID
```
GET /api/todos/{id}
```

### Create a new to-do item
```
POST /api/todos
```
Request body:
```json
{
  "title": "Task title",
  "description": "Task description"
}
```

### Update a to-do item
```
PUT /api/todos/{id}
```
Request body:
```json
{
  "title": "Updated title",
  "description": "Updated description"
}
```

### Mark a to-do item as completed
```
PATCH /api/todos/{id}/complete
```

### Delete a to-do item
```
DELETE /api/todos/{id}
```

## Project Structure

```
src/main/java/com/ToDoList/demo/
├── config/
│   └── DataLoader.java
├── controller/
│   └── TodoController.java
├── dto/
│   ├── TodoDTO.java
│   └── TodoRequest.java
├── exception/
│   ├── ErrorResponse.java
│   ├── GlobalExceptionHandler.java
│   └── ValidationErrorResponse.java
├── model/
│   └── Todo.java
├── repository/
│   └── TodoRepository.java
├── service/
│   └── TodoService.java
└── DemoApplication.java
```

## Future Enhancements

- User authentication and authorization
- Pagination for to-do items
- Categories or tags for to-do items
- Due dates and reminders
- Frontend UI (e.g., React, Angular, or Thymeleaf)
- Persistent database (e.g., MySQL, PostgreSQL)
- Docker containerization
- Unit and integration tests