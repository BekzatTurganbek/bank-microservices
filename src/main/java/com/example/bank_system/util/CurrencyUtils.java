package com.example.bank_system.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyUtils {

    public static BigDecimal convert(BigDecimal amount, BigDecimal rate) {
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }
}
