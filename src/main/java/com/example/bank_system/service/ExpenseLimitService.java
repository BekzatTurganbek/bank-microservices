package com.example.bank_system.service;

import com.example.bank_system.entity.ExpenseLimit;
import com.example.bank_system.repository.ExpenseLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseLimitService {

    @Autowired
    private ExpenseLimitRepository expenseLimitRepository;

    public BigDecimal getExpenseLimit(String category) {
        ExpenseLimit expenseLimit = expenseLimitRepository.findById(Long.valueOf(category)).orElseGet(() -> {
            ExpenseLimit limit = new ExpenseLimit();
            limit.setExpenseCategory(category);
            limit.setLimit(BigDecimal.valueOf(1000)); // Установить лимит по умолчанию в 1000 USD
            limit.setDate(LocalDateTime.now());
            return expenseLimitRepository.save(limit); // Сохранить лимит и вернуть его
        });
        return expenseLimit.getLimit(); // Возвращает лимит как BigDecimal
    }

    public ExpenseLimit setExpenseLimit(String category, BigDecimal newLimit) {
        ExpenseLimit limit = new ExpenseLimit();
        limit.setExpenseCategory(category);
        limit.setLimit(newLimit);
        limit.setDate(LocalDateTime.now());
        return expenseLimitRepository.save(limit);
    }

    public void saveExpenseLimit(ExpenseLimit limit) {
        expenseLimitRepository.save(limit);
    }

    public ExpenseLimit getCurrentLimit(String category) {
        return expenseLimitRepository.findById(Long.valueOf(category)).orElse(null);
    }

    public List<ExpenseLimit> getAllLimits() {
        return expenseLimitRepository.findAll();
    }

    public ExpenseLimit createLimit(ExpenseLimit limit) {
        return expenseLimitRepository.save(limit);
    }

    public Optional<ExpenseLimit> updateLimit(Long id, ExpenseLimit newLimit) {
        return expenseLimitRepository.findById(id)
                .map(limit -> {
                    limit.setCurrency(newLimit.getCurrency());
                    limit.setLimitAmount(newLimit.getLimitAmount());
                    return expenseLimitRepository.save(limit);
                });
    }

    public void deleteLimit(Long id) {
        expenseLimitRepository.deleteById(id);
    }
}
