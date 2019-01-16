package com.kmb.bank.config;

import com.kmb.bank.sender.Sender;
import com.kmb.bank.sender.rabbitmq.Rabbit;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.url}")
    private String rabbitmqUrl;

    @Value("${rabbitmq.username}")
    private String rabbitmqUsername;

    @Value("${rabbitmq.password}")
    private String rabbitmqPassword;

    @Value("${rabbitmq.exchange}")
    private String rabbitmqExchange;

    @Value("${rabbitmq.credit.exchange}")
    private String rabbitmqCreditExchange;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqUrl);
        connectionFactory.setUsername(rabbitmqUsername);
        connectionFactory.setPassword(rabbitmqPassword);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        return rabbitTemplate;
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(rabbitmqExchange);
    }

    @Bean
    public DirectExchange directCreditExchange() {
        return new DirectExchange(rabbitmqCreditExchange);
    }

    @Bean
    public Sender rabbitmq () {
        return new Rabbit();
    }
}
