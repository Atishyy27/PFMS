# PFMS Accounts Manager

**PFMS Accounts Manager** is a modular, extensible backend service for managing Provident Fund (PF) records. It provides secure, user-scoped CRUD operations on financial transactions (contributions & withdrawals) and is designed for future integration with federated learning and blockchain-backed audit trails.

---

## 📐 Architecture Overview

┌───────────────┐ ┌───────────────┐ ┌───────────────┐
│ REST API │──────▶│ Service │──────▶│ Repository │───▶ MySQL
│ (Spring MVC) │ │ Layer (Spring │ │ Layer (JPA) │
└───────────────┘ │ @Service) │ └───────────────┘
▲ ▲
│ │
│ │
Auth Filter Domain Model
(Spring Sec + JWT) ⬇️
┌────────────────┐
│ Fund Entity │
└────────────────┘

less
Copy
Edit

- **Authentication**: Stateless JWT (via `jjwt` 0.11.5 modules)
- **Validation**: Jakarta Bean Validation (JSR-380) + manual guards (`@PrePersist`)
- **Persistence**: Spring Data JPA with MySQL (H2 for bootstrap/dev)
- **Transactions**: Declarative `@Transactional` at service layer

---

## 🧩 Domain Model: `Fund.java`

```java
package com.example.pfms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "funds")
public class Fund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Month is required")
    @Pattern(regexp = "\\\\d{4}-\\\\d{2}", message = "Month must be YYYY-MM")
    @Column(nullable = false)
    private String month;

    @NotNull(message = "Contribution is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Must be ≥ 0")
    @Column(nullable = false)
    private BigDecimal contributionAmount;

    @NotNull(message = "Withdrawal is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Must be ≥ 0")
    @Column(nullable = false)
    private BigDecimal withdrawalAmount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // getters & setters omitted

    @PrePersist @PreUpdate
    private void validateMonthFormat() {
        if (month == null || !month.matches("\\\\d{4}-\\\\d{2}"))
            throw new IllegalArgumentException("Month must be YYYY-MM");
    }
}
Design Decisions
String-based month vs. YearMonth:

Chosen for simplicity / direct pattern validation

Future: convert to YearMonth + JPA AttributeConverter for stronger typing

Dual validation:

Jakarta annotations for request‐level errors

@PrePersist for safety in non-REST or batch processes

🛠 Service & Repository
FundRepository.java
java
Copy
Edit
public interface FundRepository extends JpaRepository<Fund, Long> {
    List<Fund> findByUser(User user);
    List<Fund> findByUserAndMonthStartingWith(User user, String yearPrefix);
}
findByUserAndMonthStartingWith optimizes “2025-__” searches (SQL LIKE '2025-%').

FundService.java
java
Copy
Edit
@Service
public class FundService {
    @Autowired private FundRepository repo;

    public Fund create(Fund f, User u) {
        f.setUser(u);
        return repo.save(f);
    }

    public List<Fund> list(User u) {
        return repo.findByUser(u);
    }

    public Fund get(Long id, User u) {
        return repo.findById(id)
                   .filter(f -> f.getUser().equals(u))
                   .orElseThrow(() -> new ResourceNotFoundException("Fund"));
    }

    @Transactional
    public Fund update(Long id, Fund in, User u) {
        Fund f = get(id, u);
        f.setMonth(in.getMonth());
        f.setContributionAmount(in.getContributionAmount());
        f.setWithdrawalAmount(in.getWithdrawalAmount());
        return repo.save(f);
    }

    @Transactional
    public void delete(Long id, User u) {
        repo.delete(get(id, u));
    }
}
Ownership checks guard cross-user access.

@Transactional on mutators ensures rollback on failure.

🔗 REST Endpoints
http
Copy
Edit
GET    /api/funds           → List all funds for authenticated user
POST   /api/funds           → Create new fund
GET    /api/funds/{id}      → Retrieve single fund by ID
PUT    /api/funds/{id}      → Update existing fund
DELETE /api/funds/{id}      → Delete fund
All endpoints require Authorization: Bearer <JWT>.

Request/response bodies are JSON; validation errors return HTTP 400 with field‐level messages.

🔐 Security Configuration
JWT Filter extracts & validates tokens per request.

Spring Security config restricts /api/** to authenticated users.

Password encoding via BCrypt in UserDetailsService.

🧪 Testing & Quality
Manual tests via Postman for each CRUD path.

Future:

JUnit + MockMvc controller tests

Integration tests with Testcontainers (MySQL)

Contract tests for API stability

📂 Project Structure
pgsql
Copy
Edit
src/main/java/com/example/pfms
├── controller   (RestControllers + exception handlers)
├── dto          (Request/response DTOs, mappers)
├── model        (JPA entities: User, Fund, …)
├── repository   (JpaRepository interfaces)
├── security     (JWT utils, filters, config)
└── service      (Business logic, transaction management)
🔭 Next Steps
Convert month field to YearMonth with JPA converter

Add CSV/Excel import-export module (Apache POI)

Build React UI & integrate with backend

Prototype federated learning ledger on blockchain
```