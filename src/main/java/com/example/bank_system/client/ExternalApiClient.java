package com.example.bank_system.client;

import com.example.bank_system.config.FeignConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "externalApiClient", url = "${external-api.url}", configuration = FeignConfig.class)
public interface ExternalApiClient {

    @GetMapping("/rate")
    BigDecimal getExchangeRate(@RequestParam String currencyPair);
}
