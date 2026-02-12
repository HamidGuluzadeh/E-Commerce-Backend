E-Commerce Microservices Ecosystem

This repository contains a fully functional, high-performance E-Commerce backend built with a Microservices Architecture. The system leverages modern Java technologies, focusing on secure authentication, synchronous service-to-service communication, and asynchronous event-driven notifications.

🚀 Technologies & Tools
Java 21 & Spring Boot 3.5.10

Spring Security & JWT: Centralized authentication with Access and Refresh token logic.

Apache Kafka: Asynchronous message streaming for order notifications.

OpenFeign: Synchronous REST communication between services.

PostgreSQL: Reliable relational database for each service.

Liquibase: Database schema versioning and migrations.

Docker & Docker Compose: Containerization for easy deployment of databases and Kafka.

🏗️ Services Overview

User Management MS: Manages user registration and security. It generates JWT tokens containing user details (including email as a custom claim) to optimize downstream service performance.

Product MS: Handles product cataloging, stock management, and category organization.

Payment MS: Integrates with payment providers (e.g., Azericard). Upon successful transaction, it publishes an event to a Kafka topic.

Notification MS: Consumes payment events from Kafka and sends automated email confirmations to users via JavaMailSender.

🛠️ Getting Started
Prerequisites
JDK 21

Docker Desktop

Gradle

Installation
Clone the repository:

git clone https://gitlab.com/HamidGuluzadeh/e-commerce.git

Start the infrastructure (PostgreSQL, Kafka, Zookeeper) using Docker:

docker-compose up -d

Run each microservice using Gradle:

./gradlew bootRun

📩 Contact

Hamid Guluzadeh - Junior Java Backend Developer

E-mail: hamidguluzadeh@gmail.com

LinkedIn: https://www.linkedin.com/in/hamid-guluzadeh-5b90072b0