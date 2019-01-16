package com.kmb.transactionlogger.listiner;

import com.kmb.transactionlogger.currency.CurrencyConverter;
import com.kmb.transactionlogger.db.mongo.models.TransferToLogDTO;
import com.kmb.transactionlogger.db.mongo.repository.MongoTransactionRepository;
import com.kmb.transactionlogger.db.postgres.CreditRepository;
import com.kmb.transactionlogger.mapper.RabbitObjectsMapper;
import com.kmb.transactionlogger.models.CreditEventDTO;
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
public class CreditListener {

    @Autowired
    private Jackson2JsonMessageConverter jackson2JsonMessageConverter;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MongoTransactionRepository mongoTransactionRepository;

    @Autowired
    private CurrencyConverter currencyConverter;

    @Autowired
    private RabbitObjectsMapper rabbitObjectsMapper;

    @Autowired
    private CreditRepository creditRepository;

    @RabbitListener(queues = "${rabbitmq.credit.queue}")
    public void listen(CreditEventDTO creditEventDTO) { 
        proceedCredit(creditEventDTO);
    }

    private void proceedCredit(CreditEventDTO creditEventDTO) {
        try {
            double amountInRecipientCurrency = getAmountInRecipientCurrency(creditEventDTO);
            updateDatabases(creditEventDTO, amountInRecipientCurrency);
        } catch (Exception e) {
            log.error("Error saving credit {}", e.getMessage());
        }
    }

    private double getAmountInRecipientCurrency(CreditEventDTO creditEventDTO) throws Exception{
        Optional<String> recipientCurrency = Optional.ofNullable(jdbcTemplate.queryForObject("SELECT currency.name FROM currency " +
                "INNER JOIN account ON account.currency_id = currency.id AND account.number = ?", String.class, creditEventDTO.getAccountNumber()));
        return recipientCurrency.map(currency-> currencyConverter
                        .convertCurrencies(creditEventDTO.getCurrency(), currency, creditEventDTO.getAmount())).orElseThrow();

    }

    @Transactional
    public void updateDatabases(CreditEventDTO creditEventDTO, double amountInRecipientCurrency) {
        try {
            creditRepository.addCreditValueToUser(creditEventDTO.getAccountNumber(), amountInRecipientCurrency);
            creditRepository.registerCredit(creditEventDTO);
            TransferToLogDTO transferDTO = rabbitObjectsMapper.creditToTransferLog(creditEventDTO, amountInRecipientCurrency);
            mongoTransactionRepository.save(transferDTO);
        } catch (Exception e) {
            log.error("Error updating database for registering credit {}", e.getMessage());
        }
    }


}
