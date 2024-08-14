package com.example.bank_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TransactionDTO {
    private Long accountFrom;
    private Long accountTo;
    private String currency;
    private BigDecimal sum;
    private String expenseCategory;
    private LocalDateTime datetime;
    private Boolean limitExceeded;


}
