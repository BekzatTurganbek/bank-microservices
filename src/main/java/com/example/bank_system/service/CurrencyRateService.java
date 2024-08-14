package com.example.bank_system.service;

import com.example.bank_system.client.CurrencyApiClient;
import com.example.bank_system.client.ExternalApiClient;
import com.example.bank_system.entity.CurrencyRate;
import com.example.bank_system.repository.CurrencyRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyRateService {

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
            return newRate;
        }
        return latestRate.get().getRate();
    }


    public List<CurrencyRate> getAllRates() {
        return currencyRateRepository.findAll();
    }

    public CurrencyRate createRate(CurrencyRate rate) {
        return currencyRateRepository.save(rate);
    }

    public Optional<CurrencyRate> updateRate(Long id, CurrencyRate newRate) {
        return currencyRateRepository.findById(id)
                .map(rate -> {
                    rate.setCurrency(newRate.getCurrency());
                    rate.setRate(newRate.getRate());
                    return currencyRateRepository.save(rate);
                });
    }

    public void deleteRate(Long id) {
        currencyRateRepository.deleteById(id);
    }

}