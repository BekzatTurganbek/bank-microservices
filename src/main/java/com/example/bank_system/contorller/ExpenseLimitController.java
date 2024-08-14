package com.example.bank_system.contorller;

import com.example.bank_system.entity.ExpenseLimit;
import com.example.bank_system.service.ExpenseLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense-limits")
public class ExpenseLimitController {
    @Autowired
    private ExpenseLimitService expenseLimitService;

    @PostMapping
    public ResponseEntity<ExpenseLimit> createLimit(@RequestBody ExpenseLimit limit) {
        expenseLimitService.saveExpenseLimit(limit);
        return ResponseEntity.status(HttpStatus.CREATED).body(limit);
    }

    @GetMapping("/{category}")
    public ResponseEntity<ExpenseLimit> getCurrentLimit(@PathVariable String category) {
        return ResponseEntity.ok(expenseLimitService.getCurrentLimit(category));
    }

    @GetMapping
    public List<ExpenseLimit> getAllLimits() {
        return expenseLimitService.getAllLimits();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseLimit> updateLimit(@PathVariable Long id, @RequestBody ExpenseLimit limit) {
        return expenseLimitService.updateLimit(id, limit)
                .map(updatedLimit -> ResponseEntity.ok().body(updatedLimit))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLimit(@PathVariable Long id) {
        expenseLimitService.deleteLimit(id);
        return ResponseEntity.noContent().build();
    }
}