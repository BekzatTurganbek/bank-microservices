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
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountFrom;
    private Long accountTo;
    private String currency;
    private BigDecimal sum;
    private String expenseCategory;
    private LocalDateTime datetime;
    private Boolean limitExceeded;
    private String currencyShortname;

    public Transaction() {

    }

    public void setSumInKzt(BigDecimal divide) {
    }

    public void setSumInUsd(BigDecimal divide) {
    }

    public void setSumInRub(BigDecimal divide) {
    }

    public Object getTimestamp() {
        return null;
    }

    public Object getAmount() {
        return null;
    }

    public void setAmount(Object amount) {
    }

    public void setTimestamp(Object timestamp) {
    }

    public Transaction(Long accountFrom, Long accountTo, String currencyShortname, BigDecimal sum, String expenseCategory, LocalDateTime datetime, boolean limitExceeded) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.currencyShortname = currencyShortname; // Используйте currencyShortname
        this.sum = sum;
        this.expenseCategory = expenseCategory;
        this.datetime = datetime;
        this.limitExceeded = limitExceeded;
    }

    // Getters and Setters
}