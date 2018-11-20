package com.kmb.bank.sender.rabbitmq;

import com.kmb.bank.sender.Sender;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class Rabbit implements Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;


    public <T> void send(T object) throws Exception {
        rabbitTemplate.convertAndSend(directExchange.getName(), "", object);

    }
}
