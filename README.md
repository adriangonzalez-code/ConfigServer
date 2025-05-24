# Config Server

## Table of Contents

1. [Description](#description)
2. [How It Works](#how-it-works)
3. [Main Features](#main-features)
4. [Architecture](#architecture)
5. [Technical Specifications](#technical-specifications)
6. [Configuration and Deployment](#configuration-and-deployment)
7. [Security](#security)
8. [API REST](#api-rest)
9. [Monitoring and Logs](#monitoring-and-logs)
10. [Installation](#installation)
11. [Development](#development)
12. [Testing](#testing)
13. [Troubleshooting](#troubleshooting)
14. [Contributing](#contributing)
15. [License](#license)

## Description

Config Server is a robust and centralized solution for application configuration management, designed to provide
efficient and secure handling of properties and secrets in an enterprise environment. It allows managing configurations
through an intuitive user interface and provides secure access through APIs.

## How It Works

1. **User Authentication & Scope Creation**

- Users log into the Config Server application
- Create a new scope which generates an access key
- The access key, URL, and scope are configured in the client API's bootstrap.properties
- Users can add, modify, and delete properties within the created scope
- All activities within the scope are logged for audit purposes

2. **Client API Integration**

- Client applications include the Config Server driver as a dependency
- During startup, the client reads connection details from bootstrap.properties
- The driver connects to Config Server backend using these credentials
- Client maintains local cache for improved performance
- Implements automatic retry and circuit breaker patterns for reliability

3. **Configuration Retrieval**

- Config Server authenticates the client request
- Returns all properties associated with the specified scope
- Client API stores retrieved properties in its environment object
- Sensitive properties are automatically decrypted before delivery
- Supports property refresh without application restart
- Maintains detailed change history for all property modifications

4. **High Availability & Performance**

- Multiple Config Server instances can run in cluster mode
- Automatic failover between instances
- Load balancing for high-traffic scenarios
- Client-side caching with configurable TTL
- Supports property versioning and rollback capabilities

## Main Features

- ✨ Centralized configuration management
- 🔐 Secure secrets handling with encryption
- 🔄 Dynamic properties update
- 📊 Complete audit system
- 🎯 Scope management for different environments/applications
- 📝 Detailed change history for properties
- 🕒 Property versioning and rollback capabilities
- 🚀 REST API for service integration
- 👥 Authentication and authorization system
- 📱 Modern and responsive user interface

## Architecture

### Main Components

├── Frontend (Angular)  
├── Backend (Spring Boot)  
├── Database (PostgreSQL)  
└── Client Driver (Java Library)  

## Technical Specifications

### Backend

- **Framework**: Spring Boot 3.x
- **Java Version**: 21
- **Main Dependencies**:
  - spring-boot-starter-web  
  - spring-boot-starter-data-jpa
  - spring-boot-starter-security
  - postgresql
  - jasypt-spring-boot-starter
  - lombok

### Frontend
- **Framework**: Angular 17+
- **Main Dependencies**:
    - @angular/material
    - @angular/flex-layout
    - ngx-toastr
    - jwt-decode

### Database

- **Engine**: PostgreSQL 15+
- **Main Schema**:
  ```
  - scopes
  - properties
  - property_change_logs
  - audit_logs
  - users
  - roles
  ```

## Configuration and Deployment

### Prerequisites

- JDK 21
- Node.js 18+
- PostgreSQL 15+
- Maven 3.8+

### Client Configuration

```yaml
config-server:
  url: http://localhost:8080
  scope: my-application
  access-key: your-access-key
```

## Security

### Authentication

- JWT (JSON Web Tokens) for user authentication
- Access tokens for client APIs authentication via scope and key
- Automatic key rotation

### Authorization

- Predefined roles:
    - ADMIN: Full access
    - EDITOR: Can modify properties
    - VIEWER: Read-only

### Encryption

- Secrets encrypted at rest using Jasypt
- TLS for communications

## API REST

### Main Endpoints

* POST   /api/v1/auth/login
* POST   /api/v1/auth/client-login
* GET    /api/v1/scopes
* POST   /api/v1/scopes
* GET    /api/v1/scopes/{scopeId}/properties
* POST   /api/v1/scopes/{scopeId}/properties
* PUT    /api/v1/properties/{propertyId}
* DELETE /api/v1/properties/{propertyId}
* GET    /api/v1/audit-logs
* GET    /api/v1/properties/{propertyId}/changes
* GET    /api/v1/properties/{propertyId}/changes/{changeId}/rollback

## Monitoring and Logs

### Available Metrics

- API response times
- Resource usage
- Access statistics
- Audit logs
- Property change history
- Configuration change frequency

### Monitoring Systems Integration

- Endpoints de actuator expuestos
- Compatibilidad con Prometheus
- Dashboards predefinidos para Grafana

## Installation

### Development Environment Setup

1. Install required tools:
  - Java JDK 21
  - Node.js 18+
  - Maven 3.8+
  - PostgreSQL 15+
  - IDE (recommended: IntelliJ IDEA or VS Code)

2. Clone the repository
3. Install dependencies:
   ```bash
   mvn clean install
   npm install
   ```
4. Configure database connection in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/configserver
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
5. Start the application:
   ```bash
   mvn spring-boot:run
   ng serve
   ```

## Development

### Version Control

- We use semantic versioning (MAJOR.MINOR.PATCH)
- Branch naming convention: feature/, bugfix/, release/
- Commit messages follow conventional commits specification

### Testing Strategy

- Unit tests with JUnit 5
- Integration tests with TestContainers
- E2E tests with Cypress
- Coverage minimum: 80%

### Deployment Environments

- Development: dev.config-server.com
- Staging: stage.config-server.com
- Production: config-server.com

## Troubleshooting

### Common Issues

1. Database Connection
  - Verify PostgreSQL is running
  - Check connection credentials
  - Ensure database exists

2. Build Failures
  - Clear Maven cache: `mvn clean`
  - Update Node modules: `npm clean-install`

3. Runtime Errors
  - Check application logs
  - Verify environment variables
  - Ensure required ports are available

## Contributing

Contributions are welcome. Please follow these steps:

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request