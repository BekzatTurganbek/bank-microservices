package com.example.bank_system.repository;


import com.example.bank_system.entity.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    Optional<CurrencyRate> findTopByCurrencyPairOrderByDateDesc(String currencyPair);
}