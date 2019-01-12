package com.kmb.bank.config;

import com.kmb.bank.mapper.CurrenciesMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;
import java.util.Random;

@Configuration
public class LogicConfig {

    @Bean
    public CurrenciesMapper currenciesMapper() {
        return new CurrenciesMapper();
    }
    
    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }
}
