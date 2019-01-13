package com.kmb.bank.db.postgres.repository;

import com.kmb.bank.models.account.AccountCurrencyDTO;
import com.kmb.bank.models.currency.CurrencyDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Repository
public class DashboardRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Set<CurrencyDTO> getCurrencies() {
       try {
           return jdbcTemplate.query("SELECT currency.name, currency_exchange.rate, currency.type FROM currency " +
                           "INNER JOIN currency_exchange on currency.id = currency_exchange.currency_id " +
                           "WHERE currency.name != 'PLN'",
                            (rs, rownum) -> CurrencyDTO.builder()
                                .setName(rs.getString("name"))
                                .setType(rs.getString("type"))
                                .setRate(rs.getDouble("rate"))
                                .build())
                            .stream()
                            .collect(Collectors.toSet());
       } catch (Exception e) {
           log.error("Error asking for currencies in dashboard view {}", e.getMessage());
           return Collections.emptySet();
       }
    }

    public AccountCurrencyDTO getMainAccount(String username) {
        try {
           return jdbcTemplate.queryForObject("SELECT account.number, account.balance, currency.name FROM account " +
                    "INNER JOIN currency ON currency.id = account.currency_id " +
                    "INNER JOIN client_account ON client_account.account_number = account.number " +
                    "INNER JOIN client ON client.pesel = client_account.client_pesel " +
                    "WHERE client.username = ? " +
                    "FETCH FIRST ROW ONLY", new Object[] {username},
                    (rs, rowNum) -> AccountCurrencyDTO.builder()
                            .setNumber(rs.getString("number"))
                            .setBalance(rs.getDouble("balance"))
                            .setCurrency(rs.getString("name"))
                            .build());
        } catch (Exception e) {
            log.error("Error getting main account in dashboard view {}", e.getMessage());
            return null;
        }
    }
}
