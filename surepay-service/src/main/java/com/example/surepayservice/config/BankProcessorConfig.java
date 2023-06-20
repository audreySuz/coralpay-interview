package com.example.surepayservice.config;

import com.example.surepayservice.clients.BankClient;
import com.example.surepayservice.clients.impl.DynamoBankClient;
import com.example.surepayservice.enums.Bank;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class BankProcessorConfig {
    private final DynamoBankClient dynamoBankClient;

    public BankProcessorConfig(DynamoBankClient dynamoBankClient) {
        this.dynamoBankClient = dynamoBankClient;
    }

    @Bean
    public Map<Bank, BankClient> bankProcessors() {
        Map<Bank, BankClient> bankProcessors = new EnumMap<>(Bank.class);
        bankProcessors.put(Bank.DB, dynamoBankClient);
        return bankProcessors;
    }
}
