package com.kmb.bank.config;

import com.kmb.bank.mapper.CurrenciesMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogicConfig {

    @Bean
    public CurrenciesMapper currenciesMapper() {
        return new CurrenciesMapper();
    }
}
