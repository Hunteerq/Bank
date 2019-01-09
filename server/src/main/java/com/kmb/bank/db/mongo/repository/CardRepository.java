package com.kmb.bank.db.mongo.repository;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Log4j2
@Repository
public class CardRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addNewCreditCard(String cardNumber, String userAccountNumber, String cvv, LocalDateTime plusYears, int cardTypeId, boolean b, double dailyContactlessLimit, double dailyTotalLimit, double dailyWebLimit) {
        try {
            jdbcTemplate.update("INSERT INTO card VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", cardNumber, userAccountNumber,
                    cvv, LocalDateTime.now().plusYears(4), cardTypeId, true,
                    dailyContactlessLimit, dailyTotalLimit, dailyWebLimit);
        } catch(Exception e) {
            log.error("Error updating database {}", e.getMessage());
        }
    }
}
