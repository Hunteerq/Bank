package com.kmb.transactionlogger;


import com.kmb.transactionlogger.models.TransferDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Log4j2
@Service
public class Listener  {

    @Autowired
    private Jackson2JsonMessageConverter jackson2JsonMessageConverter;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void listen(TransferDTO transfer) {
        try {
            log.info(transfer);
        } catch (Exception e) {
            log.debug("Error thrown while listening + " + e.getMessage());
        }

    }


}