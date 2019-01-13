package com.kmb.bank.db.postgres.repository;

import com.kmb.bank.models.credit.CreditBasicViewDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Log4j2
@Repository
public class CreditRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static double creditInterest = 1.1;

    public List<CreditBasicViewDTO> getAllCredits(String username) {
        try {
             return jdbcTemplate.query("SELECT loan.id, loan.amount, loan.months_amount, currency.name, loan.begin_date FROM loan " +
                            "INNER JOIN account ON account.number = loan.account_number " +
                            "INNER JOIN client_account ON client_account.account_number = account.number " +
                            "INNER JOIN client ON client.pesel = client_account.client_pesel AND client.username = ?" +
                            "INNER JOIN currency ON currency.id = loan.currency_id " +
                            "ORDER BY loan.id", new Object[]{username},
                            (rs, rowNum) -> CreditBasicViewDTO.builder()
                                    .setId(rs.getLong("id"))
                                    .setAmount(rs.getDouble("amount"))
                                    .setMonthsAmount(rs.getShort("months_amount"))
                                    .setCurrency(rs.getString("name"))
                                    .setStartDate(rs.getDate("begin_date"))
                                    .build());
        } catch (Exception e) {
            log.error("Error asking database for all credits {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void addCredit(String accountNumber, short currencyId, double amount, short monthsAmount) {
        try {
            jdbcTemplate.update("INSERT INTO loan VALUES(default , ?, ?, now(), ?, ?, ?, ?, ?)",
                    accountNumber, amount, monthsAmount, monthsAmount, amount, creditInterest, currencyId);
        } catch (Exception e) {
            log.error("Error adding credit {}", e.getMessage());
        }
    }
}
