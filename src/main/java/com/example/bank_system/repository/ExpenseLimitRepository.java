package com.example.bank_system.repository;

import com.example.bank_system.entity.ExpenseLimit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseLimitRepository extends JpaRepository<ExpenseLimit, Long> {
}

