package com.example.bank_system.client;

import com.example.bank_system.entity.CurrencyRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "currencyApiClient", url = "${external-api.url}")
public interface CurrencyApiClient {
    @GetMapping("/exchange-rates")
    CurrencyRate getRate(@RequestParam("pair") String currencyPair);
}
