package com.kmb.bank.services;


import com.kmb.bank.sender.Sender;
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

    public Integer queryForUsernameAndReturnColor(String username) {
        log.info("Query invoked");
        Map<String, Object> result = jdbcTemplate.queryForMap("SELECT client.color FROM client " +
                "WHERE client.username LIKE '" + username + "'" );


        return (Integer)result.get("color");
    }

    public <E> void testRabbitMq(E c1) {
        try {
            rabbitmq.send(c1);
        } catch (Exception E) {
            log.error("Error sending client to rabbitMq, " +  E.getMessage());
        }
    }

    public Integer validateUsername(String username) {
        try {
            Map<String, Object> color = jdbcTemplate.queryForMap("SELECT client.color FROM client " +
                    "INNER JOIN login ON login.username = client.username " +
                    "WHERE login.username LIKE '" + username + "'");

            log.info("Color = " + (Integer) color.get("color"));
            return (Integer) color.get("color");
        } catch (Exception E ) {
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
