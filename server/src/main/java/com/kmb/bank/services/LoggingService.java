package com.kmb.bank.services;


import com.kmb.bank.sender.Sender;
import com.kmb.bank.sender.rabbitmq.Rabbit;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Log4j2
public class LoggingService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Sender rabbitmq;


    public Short validateUsername(String username) {
        try {

            Short color = jdbcTemplate.queryForObject("SELECT login.color FROM login " +
                    "WHERE login.username LIKE '" + username + "'", Short.class);

            rabbitmq.send(color.toString());

            log.info("Color = " + color);
            return color;
        } catch (Exception E ) {
            log.error("WYJEBALO COS KURWA " + E.getMessage());
            return -200;
        }

    }

    public boolean validatePassword(String username, String password) {
        try {
            Map<String, Object> color = jdbcTemplate.queryForMap("SELECT * FROM login " +
                    "WHERE login.username LIKE '" + username + "' AND login.password LIKE '" + password + "'");
            return true;
        } catch (Exception E) {
            return false;
        }
    }
}
