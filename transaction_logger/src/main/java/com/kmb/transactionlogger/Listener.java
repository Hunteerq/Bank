package com.kmb.transactionlogger;

import com.kmb.transactionlogger.models.TransferDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Log4j2
@Service
public class Listener  {

    @Autowired
    private Jackson2JsonMessageConverter jackson2JsonMessageConverter;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void listen(TransferDTO transfer) {

        if(updateDatabase(transfer)) {
            log.info("Modifying tables completed");
        } else  {
            log.error("Error modifying tables");
        }

    }

    private boolean updateDatabase(TransferDTO transferDTO) {
        try {
            Double currentAmount = jdbcTemplate.queryForObject("SELECT account.balance FROM account " +
                    "WHERE account.number = ?", new Object[]{transferDTO.getUserAccountNumber() }, Double.class);

            if ((currentAmount != null && currentAmount - transferDTO.getAmount() >= 0)) {
                return updateTables(transferDTO);
            } else {
                log.info("Not enough funds");
                return false;
            }
        } catch(Exception e) {
            log.error("Error asking for balance " + e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean updateTables(TransferDTO transferDTO)  {
        try {
            jdbcTemplate.update("UPDATE account " +
                            "SET balance = balance - ? WHERE number = ?",
                    new Object[]{transferDTO.getAmount(), transferDTO.getUserAccountNumber()});

            jdbcTemplate.update("UPDATE account " +
                            "SET balance = balance + ? WHERE number = ?",
                    new Object[]{transferDTO.getAmount(), transferDTO.getRecipientAccountNumber()});

            log.info("Databases successfully updated");
        } catch(Exception e) {
            log.error("Erorr updating tables " + e.getMessage());
            return false;
        }
        return true;
    }
}