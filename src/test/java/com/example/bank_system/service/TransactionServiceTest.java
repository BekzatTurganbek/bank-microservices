package com.example.bank_system.service;

import com.example.bank_system.entity.Transaction;
import com.example.bank_system.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ExchangeRateService exchangeRateService;

    @Mock
    private ExpenseLimitService expenseLimitService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessTransactions() {
        Transaction transaction = new Transaction();
        transaction.setAccountFrom(1L);
        transaction.setAccountTo(2L);
        transaction.setCurrency("USD");
        transaction.setSum(BigDecimal.valueOf(100));
        transaction.setExpenseCategory("Food");
        transaction.setDatetime(LocalDateTime.now());

        when(expenseLimitService.getExpenseLimit("Food")).thenReturn(BigDecimal.valueOf(500));
        when(exchangeRateService.getExchangeRate("USD/KZT")).thenReturn(BigDecimal.valueOf(430));

        transactionService.processTransactions(List.of(transaction));

        verify(transactionRepository, times(1)).save(transaction);
        verify(exchangeRateService, times(1)).getExchangeRate("USD/KZT");
    }
}
