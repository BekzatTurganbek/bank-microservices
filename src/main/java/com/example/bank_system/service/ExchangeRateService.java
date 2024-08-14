package com.example.bank_system.service;

import com.example.bank_system.client.ExternalApiClient;
import com.example.bank_system.entity.CurrencyRate;
import com.example.bank_system.entity.Transaction;
import com.example.bank_system.repository.CurrencyRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ExchangeRateService {

    @Autowired
    private CurrencyRateRepository currencyRateRepository;


    private ExternalApiClient externalApiClient;

    public BigDecimal getExchangeRate(String currencyPair) {
        Optional<CurrencyRate> latestRate = currencyRateRepository.findTopByCurrencyPairOrderByDateDesc(currencyPair);
        if (latestRate.isEmpty() || latestRate.get().getDate().isBefore(LocalDateTime.now().minusDays(1))) {
            // Запрос к внешнему API
            BigDecimal newRate = externalApiClient.getExchangeRate(currencyPair);
            CurrencyRate currencyRate = new CurrencyRate();
            currencyRate.setCurrencyPair(currencyPair);
            currencyRate.setRate(newRate);
            currencyRate.setDate(LocalDateTime.now());
            currencyRateRepository.save(currencyRate);
            return BigDecimal.valueOf(430);
        }
        return latestRate.get().getRate();
    }

    public void saveTransactionInDifferentCurrencies(Transaction transaction) {
        BigDecimal usdRate = getExchangeRate(transaction.getCurrency() + "/USD");
        BigDecimal kztRate = getExchangeRate(transaction.getCurrency() + "/KZT");
        BigDecimal rubRate = getExchangeRate(transaction.getCurrency() + "/RUB");

        // Пример сохранения транзакции в разных валютах
        transaction.setSumInUsd(transaction.getSum().divide(usdRate, BigDecimal.ROUND_HALF_UP));
        transaction.setSumInKzt(transaction.getSum().divide(kztRate, BigDecimal.ROUND_HALF_UP));
        transaction.setSumInRub(transaction.getSum().divide(rubRate, BigDecimal.ROUND_HALF_UP));
    }
}