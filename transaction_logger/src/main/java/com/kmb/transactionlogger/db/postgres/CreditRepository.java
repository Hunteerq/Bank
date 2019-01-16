package com.kmb.transactionlogger.db.postgres;

import com.kmb.transactionlogger.models.CreditEventDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Repository
public class CreditRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static double creditInterest = 1.10;

    public void addCreditValueToUser(String accountNumber, double amountInRecipientCurrency) throws  Exception {
        jdbcTemplate.update("UPDATE account " +
                        "SET balance = balance + ? WHERE number = ?", amountInRecipientCurrency, accountNumber);
    }

    public void registerCredit(CreditEventDTO creditEventDTO) throws Exception {
        String accountNumber = creditEventDTO.getAccountNumber();
        double amount = creditEventDTO.getAmount();
        short monthsAmount = creditEventDTO.getMonthsAmount();
        short currencyId = creditEventDTO.getCurrencyId();
        jdbcTemplate.update("INSERT INTO loan VALUES(default , ?, ?, now(), ?, ?, ?, ?, ?)",
                accountNumber, amount, monthsAmount, monthsAmount, amount, creditInterest, currencyId);
    }
}
