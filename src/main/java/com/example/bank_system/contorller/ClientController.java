package com.example.bank_system.contorller;

import com.example.bank_system.entity.ExpenseLimit;
import com.example.bank_system.service.ExpenseLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/client-limits")
public class ClientController {

    @Autowired
    private ExpenseLimitService expenseLimitService;

    @PostMapping
    public ExpenseLimit setExpenseLimit(@RequestParam String category, @RequestParam BigDecimal newLimit) {
        return expenseLimitService.setExpenseLimit(category, newLimit);
    }

    @GetMapping("/{category}")
    public BigDecimal getExpenseLimit(@PathVariable String category) {
        return expenseLimitService.getExpenseLimit(category);
    }
}
