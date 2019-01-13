package com.kmb.transactionlogger;

import com.kmb.transactionlogger.currency.CurrencyConverter;
import com.kmb.transactionlogger.db.mongo.models.TransferToLogDTO;
import com.kmb.transactionlogger.db.mongo.repository.MongoTransactionRepository;
import com.kmb.transactionlogger.mapper.TransferToLogMapper;
import com.kmb.transactionlogger.models.TransferDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Log4j2
@Service
public class Listener {

    @Autowired
    private Jackson2JsonMessageConverter jackson2JsonMessageConverter;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MongoTransactionRepository mongoTransactionRepository;

    @Autowired
    private CurrencyConverter currencyConverter;

    @Autowired
    private TransferToLogMapper transferToLogMapper;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void listen(TransferDTO transfer) {
        updateDatabases(transfer);
    }

    private void updateDatabases(TransferDTO transferDTO) {
        try {
            Optional.ofNullable(jdbcTemplate.queryForObject("SELECT account.balance FROM account " +
                                 "WHERE account.number = ?", new Object[]{transferDTO.getSenderAccountNumber()}, Double.class))
                                 .ifPresentOrElse(balance -> testBalance(balance, transferDTO),
                                                    () -> log.error("Error querying database"));
        } catch (Exception e) {
            log.error("Error asking for balance " + e.getMessage());
        }
    }

    private void testBalance(Double balance, TransferDTO transferDTO) {
        if (balance - transferDTO.getAmount() >= 0) {
            updateTables(transferDTO);
        } else {
            log.error("Not enough funds");
        }
    }

    @Transactional
    public void updateTables(TransferDTO transferDTO) {
        try {
            double amountInRecipientCurrency = getAmountExchanged(transferDTO);
            jdbcTemplate.update("UPDATE account " +
                            "SET balance = balance - ? WHERE number = ?",
                    transferDTO.getAmount(), transferDTO.getSenderAccountNumber());
            jdbcTemplate.update("UPDATE account " +
                            "SET balance = balance + ? WHERE number = ?",
                    amountInRecipientCurrency, transferDTO.getRecipientAccountNumber());
            TransferToLogDTO transferToLogDTO = transferToLogMapper.transferToTransferLog(transferDTO, amountInRecipientCurrency);
            mongoTransactionRepository.save(transferToLogDTO);
        } catch (Exception e) {
            log.error("Error updating tables " + e.getMessage());
        }
    }

    private double getAmountExchanged(TransferDTO transferDTO) {
        String userCurrency = getCurrencyFromAccountNumber(transferDTO.getSenderAccountNumber());
        String recipient = getCurrencyFromAccountNumber(transferDTO.getRecipientAccountNumber());
        return currencyConverter.convertCurrencies(userCurrency, recipient, transferDTO.getAmount());
    }

    private String getCurrencyFromAccountNumber(String userAccountNumber) {
        return jdbcTemplate.queryForObject("SELECT currency.name FROM currency " +
                "INNER JOIN account ON account.currency_id = currency.id " +
                "WHERE account.number = ?", new Object[] {userAccountNumber}, String.class);
    }
}