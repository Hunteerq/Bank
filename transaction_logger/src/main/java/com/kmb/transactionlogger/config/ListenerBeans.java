package com.kmb.transactionlogger.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kmb.transactionlogger.models.TransferDTO;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ListenerBeans {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        Jackson2JsonMessageConverter  jackson2JsonMessageConverter= new Jackson2JsonMessageConverter(objectMapper());
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setDefaultType(TransferDTO.class);
        classMapper.setDefaultMapClass(TransferDTO.class);
        jackson2JsonMessageConverter.setClassMapper(classMapper);
        return new Jackson2JsonMessageConverter(objectMapper());
    }


}
