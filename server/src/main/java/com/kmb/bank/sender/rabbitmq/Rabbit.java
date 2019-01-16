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

    @Autowired
    private DirectExchange directCreditExchange;

    public <T> void sendTransfer(T object)  {
        rabbitTemplate.convertAndSend(directExchange.getName(), "", object);
    }

    public <T> void sendCredit(T object) {
        rabbitTemplate.convertAndSend(directCreditExchange.getName(), "", object);
    }
}
