package com.example.bank_system.contorller;

import com.example.bank_system.entity.CurrencyRate;
import com.example.bank_system.service.CurrencyRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;

    public CurrencyRateController(CurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }

    @GetMapping
    public List<CurrencyRate> getAllRates() {
        return currencyRateService.getAllRates();
    }

    @PostMapping
    public CurrencyRate createRate(@RequestBody CurrencyRate rate) {
        return currencyRateService.createRate(rate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyRate> updateRate(@PathVariable Long id, @RequestBody CurrencyRate rate) {
        return currencyRateService.updateRate(id, rate)
                .map(updatedRate -> ResponseEntity.ok().body(updatedRate))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRate(@PathVariable Long id) {
        currencyRateService.deleteRate(id);
        return ResponseEntity.noContent().build();
    }
}