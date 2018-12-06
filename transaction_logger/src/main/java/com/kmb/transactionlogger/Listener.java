package com.kmb.transactionlogger;

import com.kmb.transactionlogger.currency.CurrencyConverter;
import com.kmb.transactionlogger.db.mongo.repository.MongoTransactionRepository;
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

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void listen(TransferDTO transfer) {
        updateDatabases(transfer);
    }

    private void updateDatabases(TransferDTO transferDTO) {
        try {
            Optional.ofNullable(jdbcTemplate.queryForObject("SELECT account.balance FROM account " +
                               "WHERE account.number = ?", new Object[]{transferDTO.getUserAccountNumber()}, Double.class))
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
          //  double amountExchanged = getAmountExchanged(transferDTO);

            jdbcTemplate.update("UPDATE account " +
                            "SET balance = balance - ? WHERE number = ?",
                    transferDTO.getAmount(), transferDTO.getUserAccountNumber());

            jdbcTemplate.update("UPDATE account " +
                            "SET balance = balance + ? WHERE number = ?",
                    transferDTO.getAmount(), transferDTO.getRecipientAccountNumber());

            mongoTransactionRepository.save(transferDTO);

            log.info("Databases successfully updated");
        } catch (Exception e) {
            log.error("Error updating tables " + e.getMessage());
        }
    }

//    private double getAmountExchanged(TransferDTO transferDTO) {
//        String userCurrency = getCurrencyFromAccountNumber(transferDTO.getUserAccountNumber());
//        String recipient = getCurrencyFromAccountNumber(transferDTO.getRecipientAccountNumber());
//
//        double userAmountInPLN = currencyConverter.convert(transferDTO.getAmount(), userCurrency);
//        double recipientAmountInPLN = currencyConverter.convert(transferDTO.)
//
//        return 0;
//    }
//
//    private String getCurrencyFromAccountNumber(String userAccountNumber) {
//        return jdbcTemplate.queryForObject("SELECT currency.name FROM currency " +
//                "INNER JOIN account ON account.currency_id = currency.id " +
//                "WHERE account.number = ?", new Object[] {userAccountNumber}, String.class);
//    }

    // private String  getCurrencyFromAccountNumber(String acccountNumber) {
    //}
}