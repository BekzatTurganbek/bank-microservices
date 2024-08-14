package com.example.bank_system.service;

import com.example.bank_system.entity.ExpenseLimit;
import com.example.bank_system.entity.Transaction;
import com.example.bank_system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private ExpenseLimitService expenseLimitService;

    private ExecutorService executor = Executors.newFixedThreadPool(10);

    public void processTransactions(List<Transaction> transactions) {
        List<CompletableFuture<Void>> futures = transactions.stream()
                .map(transaction -> CompletableFuture.runAsync(() -> processTransaction(transaction), executor))
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    private void processTransaction(Transaction transaction) {
        BigDecimal expenseLimit = expenseLimitService.getExpenseLimit(transaction.getExpenseCategory());
        BigDecimal sumInUsd = convertToUsd(transaction.getSum(), transaction.getCurrency());
        BigDecimal currentMonthExpense = getCurrentMonthExpense(transaction.getExpenseCategory(), transaction.getDatetime());

        if (currentMonthExpense.add(sumInUsd).compareTo(expenseLimit) > 0) {
            transaction.setLimitExceeded(true);
        } else {
            transaction.setLimitExceeded(false);
        }

        transactionRepository.save(transaction);

        exchangeRateService.saveTransactionInDifferentCurrencies(transaction);
    }

    private BigDecimal convertToUsd(BigDecimal sum, String currency) {
        BigDecimal rate = exchangeRateService.getExchangeRate(currency + "/USD");
        return sum.divide(rate, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal getCurrentMonthExpense(String expenseCategory, LocalDateTime datetime) {
        // Логика для расчета текущих месячных расходов
        return BigDecimal.ZERO; // Примерное значение
    }

    public List<Transaction> getExceededTransactions() {
        return transactionRepository.findByLimitExceededTrue();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction createTransaction(Transaction transaction) {

        return transactionRepository.save(transaction);
    }


    public Optional<Transaction> updateTransaction(Long id, Transaction newTransaction) {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    transaction.setAmount(newTransaction.getAmount());
                    transaction.setCurrency(newTransaction.getCurrency());
                    transaction.setTimestamp(newTransaction.getTimestamp());
                    return transactionRepository.save(transaction);
                });
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

}
