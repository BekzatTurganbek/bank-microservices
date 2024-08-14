package com.example.bank_system.client;

import com.example.bank_system.config.FeignConfig;

public @interface FeignClient {
    String name();

    String url();


    Class<FeignConfig> configuration();
}
