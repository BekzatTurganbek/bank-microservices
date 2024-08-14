package com.example.bank_system.controller;

import com.example.bank_system.contorller.TransactionController;
import com.example.bank_system.entity.Transaction;
import com.example.bank_system.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    public void testCreateTransaction() throws Exception {
        Transaction transaction = new Transaction(1L, 2L, "KZT", new BigDecimal("10000"), "product", LocalDateTime.now(), false);
        when(transactionService.saveTransaction(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"account_from\": 1, \"account_to\": 2, \"currency_shortname\": \"KZT\", \"sum\": 10000.00, \"expense_category\": \"product\", \"datetime\": \"2022-01-30T00:00:00+06:00\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.account_from").value(1))
                .andExpect(jsonPath("$.account_to").value(2))
                .andExpect(jsonPath("$.currency_shortname").value("KZT"))
                .andExpect(jsonPath("$.sum").value(10000.00))
                .andExpect(jsonPath("$.expense_category").value("product"));
    }

    @Test
    public void testGetExceededTransactions() throws Exception {
        when(transactionService.getExceededTransactions()).thenReturn(Collections.singletonList(new Transaction(1L, 2L, "KZT", new BigDecimal("10000"), "product", LocalDateTime.now(), true)));

        mockMvc.perform(get("/api/transactions/exceeded")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].limit_exceeded").value(true));
    }

    @Test
    public void testProcessTransactions() throws Exception {
        mockMvc.perform(post("/api/transactions/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{ \"account_from\": 1, \"account_to\": 2, \"currency_shortname\": \"KZT\", \"sum\": 10000.00, \"expense_category\": \"product\", \"datetime\": \"2022-01-30T00:00:00+06:00\" }, { \"account_from\": 3, \"account_to\": 4, \"currency_shortname\": \"RUB\", \"sum\": 5000.00, \"expense_category\": \"service\", \"datetime\": \"2022-01-30T00:00:00+06:00\" }]"))
                .andExpect(status().isOk());

        verify(transactionService, Mockito.times(1)).processTransactions(anyList());
    }
}
