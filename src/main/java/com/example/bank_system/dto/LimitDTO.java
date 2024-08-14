package com.example.bank_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor

public class LimitDTO {
    private String expenseCategory;
    private BigDecimal limit;


}
