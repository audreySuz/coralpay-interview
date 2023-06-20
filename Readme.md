# Introduction
This is a modular springboot application containing 2 modules:
- **dynamo-bank-service**: A service that exposes a REST API to generate dynamic virtual bank accounts. It exposes a single enpoint that receives a JSON payload.
   - **dependencies**:
      - spring-boot-starter-web
      - spring-boot-starter-data-jpa
      - mysql-connector-java
- **dynamo-bank-service-client**: This service provides API for merchant integration, while consuming the API to generate dynamic virtual bank accounts. It exposes a single enpoint that receives a JSON payload.
   - **dependencies**:
      - spring-boot-starter-web
      - spring-boot-starter-data-jpa
      - mysql-connector-java.

# Possible Improvements
- Add a caching layer to the surepay-service to improve performance, and not hit the database or the API for every request.
- Validate the input payload in the surepay-service.
- Properly handle exceptions in both services.
- Logging of requests and responses.
- Masking of sensitive data in the logs.
- Use of migration tools like Flyway or Liquibase to manage database schema changes.
- Asynchronous processing of requests in the surepay-service.
- Secure the API endpoints using Spring Security.(With JWT or OAuth2 or API Keys)


# Getting Started
### Reference Documentation
For further reference, please see the swagger documentation at http://localhost:8080/swagger-ui.html

# Build and Run
### Prerequisites
- Java 8
- Maven 3.6.3
- MySQL 8.0.23

### Build
- Clone the repository
- Run `mvn clean install` from the root directory
- Run `mvn clean install` from the dynamo-bank-service directory
- Run `mvn clean install` from the dynamo-bank-service-client directory

### Run
- Run `mvn spring-boot:run` from the dynamo-bank-service directory

### Test
- Run `mvn test` from the surepay-service directory

# Notes
MySQL is used as the database for this project. Update the application.properties file with your local MySQL credentials.
