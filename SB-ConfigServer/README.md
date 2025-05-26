# Configuration Server 🔧

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.org/projects/jdk/17/)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-Config-brightgreen.svg)](https://spring.io/projects/spring-cloud-config)
[![Docker](https://img.shields.io/badge/Docker-ready-blue.svg)](https://www.docker.com/)

This is the backend configuration server component responsible for managing centralized configuration properties for
microservices in the distributed system. It provides a reliable and scalable way to maintain configuration across all
services.

## Project Overview

- Backend configuration server built with Spring Cloud Config
- Manages environment-specific properties for different microservices
- Supports multiple environments (dev, qa, prod)
- Properties are structured in environment-specific folders

## Prerequisites 📋

- Java 21 or higher (OpenJDK recommended)
- Maven 3.8+ (for building and dependency management)
- Docker Desktop (for containerization and running required services)
- Git (for version control and configuration management)
- PostgreSQL (will be run in Docker)

## Setup Guide 🚀

### 1. Initial Setup

1. Clone the repository
2. Create a Docker Hub account at https://hub.docker.com/
3. Install Docker Desktop and sign in with your Docker Hub credentials
4. Verify installation with `docker --version`

### 2. Environment Setup

Configure your environment by setting up the necessary Docker containers. The following environment variables will be
used for PostgreSQL:

- `POSTGRES_USER`: Database user (default: postgres)
- `POSTGRES_PASSWORD`: Database password
- `POSTGRES_DB`: Database name
- `POSTGRES_PORT`: Port mapping (default: 5432)

### 3. Database Setup

1. Ensure no PostgreSQL instances are running on ports 5432, 5433, or 5434
2. Start PostgreSQL container using Docker:

```bash
  docker run -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=admin123 -d -p 5432:5432 postgres
```

### 4. Build and Run the Application

1. Open your IDE and import the project as a Maven project
2. Add VM options in the Run Configuration window:

```properties
  -Dspring.config.location=classpath:properties/common/,classpath:properties/local/
```