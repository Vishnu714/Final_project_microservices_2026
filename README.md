

# 🚀 Microservices E-Commerce Backend (Java 17 + Spring Boot)

## 📌 Overview

This project is a **Microservices-based E-Commerce Backend System** built using **Java 17**, **Spring Boot**, and **Spring Cloud**.

It demonstrates:

* Microservices Architecture
* JWT-based Security
* API Gateway + Service Discovery
* Aggregation Pattern
* Circuit Breaker (Resilience4j)
* Dev & Prod Profiles
* Docker for Production
* Basic Logging
* JUnit Testing
* CI/CD (Jenkins – Pipeline planned)

---

## 🏗️ Architecture

### 🔹 Core Services

* **Product Service** – Manages product catalog (JPA + DB)
* **Inventory Service** – Manages stock levels
* **Order Service** – Handles order lifecycle + Circuit Breaker
* **Auth Service** – JWT-based authentication & role management
* **Aggregator Service** – Aggregates data from Product, Inventory & Order
* **API Gateway** – Central routing + global JWT validation
* **Service Registry** – Eureka discovery
* **Config Server** – Centralized configuration management

---

## 🔐 Security

* JWT Authentication implemented in **Auth Service**
* JWT validation enforced globally in **API Gateway**
* Role-based authorization:

  * `ADMIN` → Create / Update / Delete
  * `USER` → Read / Place orders
* Token propagation to downstream services

---

## 🔄 Microservice Patterns Implemented

### ✅ Aggregation Pattern

* Dedicated **Aggregator Service**
* Combines:

  * Product details
  * Inventory quantity
  * Orders summary

### ✅ Circuit Breaker

* Implemented using **Resilience4j**
* Applied in:

  * Order → Inventory calls
  * Aggregator → Product/Inventory/Order calls

### ✅ Service Discovery

* Netflix Eureka

### ✅ Centralized Configuration

* Spring Cloud Config (Native profile)

---

## 🐳 Environments

### 🔹 DEV Profile

* H2 In-Memory Database
* Local execution
* Used for development & testing

### 🔹 PROD Profile

* PostgreSQL
* Docker containerized database
* Externalized configuration via Config Server

Switch profile:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev -pl product-service
```

---

## 🐳 Docker (Production)

* PostgreSQL container for prod profile
* Services can be containerized
* Production profile connects to Postgres

Example:

```bash
docker run -d \
  --name product-postgres \
  -e POSTGRES_DB=productdb \
  -e POSTGRES_USER=prod_user \
  -e POSTGRES_PASSWORD=prod_password \
  -p 5432:5432 postgres:16
```

---

## 🧪 Testing

Implemented:

* ✅ DTO Tests
* ✅ Service Layer Tests (Mockito)
* ✅ Repository Tests (@DataJpaTest)
* ✅ Controller Tests (@WebMvcTest)
* ✅ Integration Test (Full SpringBootTest)

JUnit 5 used for testing.

---

## 📊 Observability

* Spring Boot Actuator
* `/health`
* `/metrics`
* Circuit Breaker monitoring endpoints
* Basic logging enabled

(*Splunk integration not implemented yet*)

---

## 📄 API Documentation

Swagger / OpenAPI implemented per service.

Access:

```
http://localhost:{port}/swagger-ui.html
```

---

## ⚙️ CI/CD

Planned Jenkins Pipeline:

* Build (Maven)
* Run Tests
* Build Docker Image
* Push to Registry
* Deployment Stage

(Currently partially implemented)

---

## 🛠 Tech Stack

* Java 17
* Spring Boot 3.x
* Spring Cloud 2023.x
* Spring Security + JWT
* Spring Cloud Gateway
* Eureka
* Resilience4j
* PostgreSQL / H2
* Docker
* Maven
* JUnit 5

---

## 📦 Project Structure

```
config-server
service-registry
api-gateway
auth-service
product-service
inventory-service
order-service
aggregator-service
project-configurations - Github repo 
```

---

## 🎯 Key Features Achieved

✔ Microservices architecture
✔ JWT authentication & role-based access
✔ Aggregation pattern
✔ Circuit breaker resilience
✔ Dev & Prod profile setup
✔ Dockerized production DB
✔ Centralized configuration
✔ JUnit test coverage
✔ Swagger documentation

---

## 🚀 Future Improvements

* Full CI/CD automation

* Centralized logging (Splunk / ELK)
* Distributed tracing
* Message broker (Kafka) for async order events

---

## 👨‍💻 Author

Built as a Capstone Microservices Project demonstrating enterprise patterns and production-ready architecture.


