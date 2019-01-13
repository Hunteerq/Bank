package com.kmb.bank.db.postgres.repository;

import com.kmb.bank.models.account.AccountBasicViewDTO;
import com.kmb.bank.models.account.AccountCurrencyDTO;
import com.kmb.bank.models.account.AccountSpecifiedViewDTO;
import com.kmb.bank.models.account.AccountTypeDTO;
import com.kmb.bank.models.currency.CurrencyChooseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

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
            return validate > 0;
        } catch (Exception e) {
            log.info("Error asking database if account number belongs to username {}", e.getMessage());
            return false;
        }
    }

    public List<AccountCurrencyDTO> getAccountNumbersWithBalanceAndCurrency(String username) {
        try {
            return jdbcTemplate.query("SELECT account.number, currency.name, account_type.type, account.balance FROM account " +
                            "INNER JOIN client_account ON client_account.account_number = account.number " +
                            "INNER JOIN client ON client.pesel = client_account.client_pesel " +
                            "INNER JOIN currency ON account.currency_id = currency.id " +
                            "INNER JOIN account_type ON account_type.id = account.account_type_id " +
                            "WHERE client.username = ?", new Object[]{username},
                    (rs, rowNum) -> AccountCurrencyDTO.builder()
                            .setNumber(rs.getString("number"))
                            .setCurrency(rs.getString("name"))
                            .setType(rs.getString("type"))
                            .setBalance(rs.getDouble("balance"))
                            .build());
        } catch (Exception e) {
            log.error("Error asking database for basic account number and balance {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<AccountBasicViewDTO> getAccountNumbers(String username) {
        try {
            return jdbcTemplate.query("SELECT account.number, account.balance FROM account " +
                    "INNER JOIN client_account on account.number = client_account.account_number " +
                    "INNER JOIN client on client_account.client_pesel = client.pesel " +
                    "WHERE client.username = ?", new Object[]{username},
                    (rs, rowNum) -> AccountBasicViewDTO.builder()
                        .setBalance(rs.getDouble("balance"))
                        .setNumber(rs.getString("number"))
                        .build());
        } catch (Exception e) {
            log.error("Error getting Account number and balance for adding card view {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<CurrencyChooseDTO> getCurrencies() {
        try {
            return jdbcTemplate.query("SELECT currency.name, currency.id FROM currency",
                    (rs,rowNum) -> CurrencyChooseDTO.builder()
                            .setId(rs.getInt("id"))
                            .setName(rs.getString("name"))
                            .build());
        } catch (Exception e) {
            log.error("Error asking database for currencies names and theirs ids {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<AccountTypeDTO> getAccountTypes() {
        try {
            return jdbcTemplate.query("SELECT account_type.id, account_type.type FROM account_type",
                    (rs, rowNum) -> AccountTypeDTO.builder()
                            .setId(rs.getInt("id"))
                            .setType(rs.getString("type"))
                            .build());
        } catch (Exception e) {
            log.error("Error asking database fo account types with their ids {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public String getUserPesel(String username) throws Exception{
        return jdbcTemplate.queryForObject("SELECT client.pesel FROM client " +
                "WHERE username = ?", new Object[] {username}, String.class);
    }

    @Transactional
    public void createAccount(String newNumberAccountNumber, String userPesel, int accountTypeId, int currencyId) {
        try {
            jdbcTemplate.update("INSERT INTO account VALUES(?, 0.0, ?, ?, now(), true, true)",
                    newNumberAccountNumber, currencyId, accountTypeId);
            jdbcTemplate.update("INSERT INTO client_account VALUES(DEFAULT, ?, ?)"
                    ,userPesel, newNumberAccountNumber);
        } catch (Exception e) {
            log.error("Error adding new account {}", e.getMessage());
        }
    }


    public AccountSpecifiedViewDTO getSpecifiedAccountWithTypeBalanceAndCurrency(String accountNumber) {
        try {
            return jdbcTemplate.queryForObject("SELECT currency.name, account_type.type, account.balance, account.opening_date, account.active FROM account " +
                            "INNER JOIN currency ON account.currency_id = currency.id " +
                            "INNER JOIN account_type ON account_type.id = account.account_type_id " +
                            "WHERE account.number = ?", new Object[] {accountNumber},
                    (rs, rowNum) -> AccountSpecifiedViewDTO.builder()
                            .setNumber(accountNumber)
                            .setBalance(rs.getDouble("balance"))
                            .setCurrency(rs.getString("name"))
                            .setType(rs.getString("type"))
                            .setOpeningDate(rs.getDate("opening_date"))
                            .setActive(rs.getBoolean("active"))
                            .build());
        } catch (Exception e) {
            log.error("Error asking database for basic account number and balance {}", e.getMessage());
        }
        return null;
    }

    public void changeAccountStatus(String accountNumber, boolean status) {
        try {
            jdbcTemplate.update("UPDATE account " +
                    "SET active = ? " +
                    "WHERE number = ?", status, accountNumber);
        } catch (Exception e) {
            log.error("Error changing account status {}", e.getMessage());
        }
    }

    @Transactional
    public void deleteSpecifiedAccount(String accountNumber) {
        try {
            jdbcTemplate.update("DELETE FROM client_account WHERE account_number = ?", accountNumber);
            jdbcTemplate.update("DELETE FROM account WHERE number = ?", accountNumber);
        } catch (Exception e) {
            log.error("Error deleting account {} : {}", accountNumber, e.getMessage());
        }
    }
}
