# 📝 E-Notes Backend API

This project is a **robust and scalable backend** for a notes management application, built using **Java** and the *
*Spring Boot** framework.
It provides a full-featured **RESTful API** for managing **users, notes, categories, todos, favourites**, and more.

🔗 **Repository**: [eNotes Backend API](https://github.com/RofixWork/enotes-api-service)

---

## 🚀 Core Features

* **User Management**

    * Secure user registration & authentication with **Spring Security** and **JWT**
    * Account verification via **email**
    * Password reset functionality

* **Notes Management**

    * Full **CRUD** (Create, Read, Update, Delete, Copy, Recycle Bin, Download, etc.)
    * Association with categories, favourites, and todos

* **Soft Deletes**

    * Records are not permanently removed → marked as inactive for easy restoration

* **File Handling**

    * Upload & download files attached to notes

* **Scheduled Tasks**

    * Automatic cleanup: permanently removes deleted notes after **15 minutes**

---

## ⚙️ Technical Stack & Best Practices

* **Framework**: Spring Boot with **Spring Profiles** (`dev`, `prod`, `uat`, `test`) using `application.yaml`
* **Database**:

    * MySQL (Development)
    * PostgreSQL (Production)
* **ORM**: Hibernate JPA + Specifications for flexible queries
* **Security**: Spring Security + JWT for stateless authentication
* **Validation**: Hibernate Validator for data integrity
* **Auditing**: Auto-set `createdBy`, `updatedBy`, `createdAt`, `updatedAt`
* **AOP**: Aspect-Oriented Programming for cross-cutting concerns
* **Caching**: Improve API performance & reduce DB load
* **Logging**: Logback for structured application logs
* **Error Handling**: Centralized exception handling with consistent responses
* **API Documentation**: Swagger/OpenAPI for easy integration with frontend
* **Monitoring**: Spring Boot Actuator for health & performance metrics
* **Code Quality**: SonarQube integration for static analysis
* **Testing**: Unit & integration tests for **Category Service/Controller** and **Authentication**

---

## 📂 Entities

* **User** 👤 → Represents user accounts
* **Category** 🗂️ → Groups and organizes notes
* **Favourite** ⭐ → Tracks user’s favourite notes
* **Note** 📝 → Main entity for notes
* **Todo** ✅ → To-do list items linked to notes
* **Cache** ⚡ → Represents cached data

---

## 🏁 Getting Started

1. **Clone the repository**

   ```bash
   git clone https://github.com/RofixWork/enotes-api-service.git
   cd enotes-api-service
   ```

2. **Configure your database**
   Update connection details in:

   ```
   src/main/resources/application.yaml
   ```

3. **Run the application**

   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the API**

    * Base URL → `http://localhost:8080`
    * Swagger Docs → `http://localhost:8080/swagger-ui.html`
    * Actuator → `http://localhost:8080/actuator`

---

## 🧪 Testing

Run unit & integration tests:

```bash
mvn test
```

---

## 🌱 Branching Strategy

* `dev` → Active development
* `uat` → User Acceptance Testing
* `test` → QA environment
* `prod` → Production

---

## 🛠️ Tech Stack

* **Java 17+**
* **Spring Boot**
* **Spring Security + JWT**
* **Hibernate JPA**
* **MySQL / PostgreSQL**
* **Swagger / OpenAPI**
* **Actuator**
* **SonarQube**
* **JUnit + Mockito**

---
✨ Created with care by **Abdessamad Ait Oughenja**