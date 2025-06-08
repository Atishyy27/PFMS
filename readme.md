# PFMS Accounts Manager

**PFMS Accounts Manager** is a full-stack project built to manage Provident Fund (PF) accounts for employees and organizations. It includes a Spring Boot backend, a MySQL database, a React frontend, and secure data import/export via CSV/Excel, with JWT-based authentication for access control.

---

## 🚀 Project Status

This project is actively under development by a student team. We are documenting our journey step by step, from setup to core feature implementation, following modern open-source development practices. This `README.md` will serve as a central knowledge base and progress journal.

---

## ⚙️ Tech Stack

- **Backend:** Java 17, Spring Boot, Spring Security, Spring Data JPA
- **Database:** MySQL (initially H2 for bootstrapping)
- **Frontend:** React (planned)
- **Authentication:** JWT-based (planned)
- **Data Import:** Excel/CSV ingestion via Apache POI or similar (planned)

---

## 📘 Project Setup (Backend)

### 1. Repository Initialization

We started by creating a GitHub repository named `pfms-accounts-manager` and added a high-level project description. Local development environments were set up with:

- Java 17
- Maven
- IDE (IntelliJ IDEA / Eclipse / VS Code with Java plugins)

> 🟢 Commit:  
> `feat: initialize repo and README with project overview`

---

### 2. Spring Boot Skeleton

Using [start.spring.io](https://start.spring.io), we bootstrapped a minimal Spring Boot application with dependencies:

- Spring Web
- Spring Data JPA
- Spring Security
- MySQL Driver (initially used H2 for quick testing)

We verified that the project starts successfully by adding a test controller:

```java
@RestController
public class HelloController {
    @GetMapping("/")
    public String hello() {
        return "PFMS Hello";
    }
}
```