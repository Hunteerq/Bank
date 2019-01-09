package com.kmb.bank.db.mongo.repository;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class AccountRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Boolean ifAccountNumberBelongsToUser(String accountNumber, String username){
        try {
            Integer validate  = jdbcTemplate.queryForObject("SELECT count(*) from account " +
                    "INNER JOIN client_account on client_account.account_number = account.number " +
                    "INNER JOIN client on client.pesel = client_account.client_pesel " +
                    "WHERE client.username = ? AND account.number = ?", new Object[] {username, accountNumber}, Integer.class);

            return validate > 0 ? true : false;
        } catch(Exception e) {
            log.info("Error asking database if account number belongs to username {}", e.getMessage());
            return false;
        }
    }
}
