package com.example.bank_system.service;

import com.example.bank_system.client.ExternalApiClient;
import com.example.bank_system.entity.CurrencyRate;
import com.example.bank_system.repository.CurrencyRateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CurrencyRateServiceTest {

    @InjectMocks
    private CurrencyRateService currencyRateService;

    @Mock
    private CurrencyRateRepository currencyRateRepository;

    @Mock
    private ExternalApiClient externalApiClient;

    public CurrencyRateServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetExchangeRate_existingRate() {
        CurrencyRate rate = new CurrencyRate();
        rate.setCurrencyPair("USD/KZT");
        rate.setRate(BigDecimal.valueOf(430));
        rate.setDate(LocalDateTime.now());

        when(currencyRateRepository.findTopByCurrencyPairOrderByDateDesc("USD/KZT")).thenReturn(Optional.of(rate));

        BigDecimal result = currencyRateService.getExchangeRate("USD/KZT");
        assertEquals(BigDecimal.valueOf(430), result);

        verify(currencyRateRepository, times(1)).findTopByCurrencyPairOrderByDateDesc("USD/KZT");
        verifyNoInteractions(externalApiClient);
    }

    @Test
    public void testGetExchangeRate_newRate() {
        when(currencyRateRepository.findTopByCurrencyPairOrderByDateDesc("USD/KZT")).thenReturn(Optional.empty());
        when(externalApiClient.getExchangeRate("USD/KZT")).thenReturn(BigDecimal.valueOf(440));

        BigDecimal result = currencyRateService.getExchangeRate("USD/KZT");
        assertEquals(BigDecimal.valueOf(440), result);

        verify(currencyRateRepository, times(1)).findTopByCurrencyPairOrderByDateDesc("USD/KZT");
        verify(externalApiClient, times(1)).getExchangeRate("USD/KZT");
    }
}
