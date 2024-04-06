# ConsumeAPI

ConsumeAPI is a Spring Boot microservice designed to demonstrate RESTful API consumption and management of user posts. It provides a simple interface for interacting with external APIs, showcasing CRUD operations on user posts.

## Features

- **CRUD Operations:** Create, Read, Update, and Delete posts.
- **External API Integration:** Validates `userId` and fetches posts using external APIs.
- **Swagger Documentation:** Interactive API documentation using Springdoc OpenAPI.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- JDK 21
- Maven
- Docker

### Installing

1. **Clone the repository**

```bash
git clone https://github.com/marosmamrak/consume-api.git
cd consume-api

```
2. **Build the application
```bash
mvn clean install
```

3. **Accessing the application
  Web Interface: Open http://localhost:8080 in your web browser.
  Swagger UI: Navigate to http://localhost:8080/swagger-ui.html to explore the RESTful API.

4. **Built with
  Spring Boot - The web framework used
  Maven - Dependency Management
  Springdoc OpenAPI - API documentation

5. **Authors
     Maros Mamrak - Initial work - marosmamrak

