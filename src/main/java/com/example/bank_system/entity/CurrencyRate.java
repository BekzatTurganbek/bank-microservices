package com.example.bank_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@Entity
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String currencyPair;
    private BigDecimal rate;
    private LocalDateTime date;

    public CurrencyRate() {

    }

    public Object getCurrency() {
        return null;
    }

    public void setCurrency(Object currency) {
    }

    // Getters and Setters
}
