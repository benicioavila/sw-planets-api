# Planet API - Spring REST

## Overview
The **Planet API** is a RESTful API built with Spring Boot, designed to manage and retrieve information about planets. It is intended to serve as the backend for a planetary application, providing endpoints to create, read, update, and delete planet-related data. The API is rigorously tested with both unit and integration tests to ensure reliability and maintainability.

## Features
- CRUD operations for planets
- Search planets by name or other criteria
- Comprehensive testing with unit and integration tests

## Requirements
- Java 17 or higher
- Maven 3.6 or higher
- Spring Boot 3.x
- H2 Database (for development and testing purposes)
- JUnit 5 (for testing)

## Getting Started

### Clone the Repository
```bash
git clone https://github.com/your-repo/planet-api.git
cd planet-api
```

### Build the Project
Use Maven to build the project and run tests:
```bash
mvn clean install
```

### Run the Application
Start the application using the Spring Boot Maven plugin:
```bash
mvn spring-boot:run
```
The application will be available at `http://localhost:8080`.

### API Endpoints
#### Base URL
```
http://localhost:8080/api/v1/planets
```

#### Endpoints
| Method | Endpoint               | Description                   |
|--------|------------------------|-------------------------------|
| GET    | `/`                    | List all planets              |
| GET    | `/{id}`                | Get planet by ID              |
| POST   | `/`                    | Create a new planet           |
| PUT    | `/{id}`                | Update an existing planet     |
| DELETE | `/{id}`                | Delete a planet               |

### Example Request (Create a Planet)
#### Request
```http
POST /api/v1/planets HTTP/1.1
Content-Type: application/json

{
  "name": "Earth",
  "diameter": 12742,
  "climate": "Temperate",
  "terrain": "Forests, Oceans, Mountains"
}
```

#### Response
```json
{
  "id": 1,
  "name": "Earth",
  "diameter": 12742,
  "climate": "Temperate",
  "terrain": "Forests, Oceans, Mountains"
}
```

## Testing
The application is covered by unit and integration tests to ensure functionality:

### Running Tests
Run the tests using Maven:
```bash
mvn test
```

### Test Coverage
- **Unit Tests**: Verify the behavior of individual components (e.g., services, repositories).
- **Integration Tests**: Validate the interaction between components and the database.

## Database
The API uses an in-memory H2 database for development and testing purposes. The database schema is automatically created and managed by Spring Data JPA.

### H2 Console
The H2 database console is available at:
```
http://localhost:8080/h2-console
```
Default credentials:
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `password`

## Technologies Used
- **Spring Boot**: Backend framework
- **Spring Data JPA**: ORM for database interactions
- **H2 Database**: In-memory database
- **JUnit 5**: Testing framework

## Contribution
Contributions are welcome! Please fork the repository and submit a pull request with your changes.

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.

