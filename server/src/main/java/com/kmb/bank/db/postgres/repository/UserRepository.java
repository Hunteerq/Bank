package com.kmb.bank.db.postgres.repository;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getUserColor(String username) {
        try {
            return jdbcTemplate.queryForObject("SELECT login.color FROM login " +
                       "WHERE login.username = ?", new Object[] {username}, String.class);
        } catch (Exception e) {
            log.error("Error getting user's color {}", e.getMessage());
            return null;
        }
    }

    public Integer validateUsernameAndPassword(String username, String password) {
        try {
            String encodedPassword = DigestUtils.md5Hex(password);
            return jdbcTemplate.queryForObject("SELECT count(*) FROM login " +
                    "WHERE login.username = ? AND login.password = ?", new Object[] {username, encodedPassword},  Integer.class);
        } catch (Exception e) {
            log.error("Error validating username and password in database {}" , e.getMessage());
            return 0;
        }
    }

    public String getUserNameAndSurname(String username) {
        try {
            return jdbcTemplate.queryForObject("SELECT CONCAT(name, ' ',surname) FROM client " +
                    "WHERE username = ?", new Object[] {username}, String.class);
        } catch (Exception e) {
            log.error("Error asking database for name and surname {}", e.getMessage());
            return null;
        }
    }
}
