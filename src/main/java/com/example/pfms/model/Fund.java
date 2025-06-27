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
    @Pattern(regexp = "\\d{4}-\\d{2}", message = "Month must be in YYYY-MM format")
    @Column(nullable = false)
    private String month;

    @NotNull(message = "Contribution amount is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Contribution must be non-negative")
    @Column(nullable = false)
    private BigDecimal contributionAmount;

    @NotNull(message = "Withdrawal amount is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Withdrawal must be non-negative")
    @Column(nullable = false)
    private BigDecimal withdrawalAmount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getContributionAmount() {
        return contributionAmount;
    }

    public void setContributionAmount(BigDecimal contributionAmount) {
        this.contributionAmount = contributionAmount;
    }

    public BigDecimal getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(BigDecimal withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Validate month format at persistence level
    @PrePersist
    @PreUpdate
    private void validateMonthFormat() {
        if (month == null || !month.matches("\\d{4}-\\d{2}")) {
            throw new IllegalArgumentException("Month must be in YYYY-MM format");
        }
    }
}
