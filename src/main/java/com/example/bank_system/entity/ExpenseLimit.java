package com.example.bank_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class ExpenseLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String expenseCategory;
    private BigDecimal limit;
    private LocalDateTime date;

    public ExpenseLimit() {

    }

    public Object getCurrency() {
        return null;
    }

    public void setCurrency(Object currency) {
    }

    public Object getLimitAmount() {
        return null;
    }

    public void setLimitAmount(Object limitAmount) {
    }

    // Getters and Setters
}
