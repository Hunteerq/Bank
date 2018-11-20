package com.kmb.bank.services;


import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class LoggingService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Short validateUsername(String username) {
        try {
            Short color = jdbcTemplate.queryForObject("SELECT login.color FROM login " +
                    "WHERE login.username = '" + username + "'", Short.class);
            log.info("Color = " + color);

            return color;
        } catch (DataAccessException E) {
            log.error("Error, too many users with the same username " + E.getMessage());

            return -1;
        }

    }

    public boolean validatePassword(String username, String password) {
        try {
            String encodedPassword = DigestUtils.md5Hex(password);
            String ifValidated =  jdbcTemplate.queryForObject("SELECT login.username FROM login " +
                    "WHERE login.username = '" + username + "' AND login.password = '" + encodedPassword + "'", String.class);
            log.info("After validating = " + ifValidated);

            return true;
        } catch (DataAccessException E) {
            log.info("Not validated " + E.getMessage());

            return false;
        }
    }
}
