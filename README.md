#IMPORTANT NOTE
This project was created using Junie code agent from intellij idea, this was a test run to see its
capabilities. All i did was give it correct inputs and debugg its outputs for obvious mistakes.



# To-Do List Application

A full-stack to-do list application with a Spring Boot backend and a simple HTML/CSS/JavaScript frontend.

## Project Structure

This project is organized into two main directories:

- **backend**: Contains the Spring Boot RESTful API for managing to-do items
- **frontend**: Contains a simple HTML/CSS/JavaScript UI for interacting with the API

## Backend

The backend is a Spring Boot application that provides a RESTful API for managing to-do items.

### Features

- Create, read, update, and delete to-do items
- Mark to-do items as completed
- Filter to-do items by completion status
- Search to-do items by title or description
- Validation for to-do item fields
- In-memory H2 database for development and testing
- Sample data loaded automatically for testing

### Technologies Used

- Java 17
- Spring Boot 3.5.0
- Spring Data JPA
- H2 Database
- Lombok
- Jakarta Validation

### Running the Backend

1. Navigate to the backend directory:
   ```
   cd backend
   ```

2. Build the project:
   ```
   mvn clean install
   ```

3. Run the application:
   ```
   mvn spring-boot:run
   ```

The backend API will start on port 8080 by default.

### API Endpoints

- **GET /api/todos**: Get all to-do items
- **GET /api/todos?completed=true**: Get to-do items by completion status
- **GET /api/todos?search=keyword**: Search to-do items
- **GET /api/todos/{id}**: Get a to-do item by ID
- **POST /api/todos**: Create a new to-do item
- **PUT /api/todos/{id}**: Update a to-do item
- **PATCH /api/todos/{id}/complete**: Mark a to-do item as completed
- **DELETE /api/todos/{id}**: Delete a to-do item

## Frontend

The frontend is a simple HTML/CSS/JavaScript application that interacts with the backend API.

### Features

- View all to-do items
- Add new to-do items
- Mark to-do items as completed
- Delete to-do items
- Filter to-do items by completion status
- Search to-do items by title or description

### Technologies Used

- HTML5
- CSS3
- JavaScript (ES6+)
- Fetch API for HTTP requests

### Running the Frontend

Since the frontend is a simple static website, you can open it directly in a web browser:

1. Navigate to the frontend directory:
   ```
   cd frontend
   ```

2. Open the index.html file in your web browser:
   - Double-click on the index.html file, or
   - Use a local development server like Live Server in VS Code, or
   - Use Python's built-in HTTP server:
     ```
     python -m http.server
     ```
     Then open http://localhost:8000 in your browser

## Important Notes

- The frontend is configured to connect to the backend at `http://localhost:8080/api/todos`. If your backend is running on a different URL, you'll need to update the `API_BASE_URL` variable in the `script.js` file.
- For production use, consider implementing user authentication, using a persistent database, and deploying both the frontend and backend to appropriate hosting services.

## Future Enhancements

- User authentication and authorization
- Pagination for to-do items
- Categories or tags for to-do items
- Due dates and reminders
- More sophisticated UI with a modern framework (e.g., React, Angular, or Vue)
- Persistent database (e.g., MySQL, PostgreSQL)
- Docker containerization
- Unit and integration tests
