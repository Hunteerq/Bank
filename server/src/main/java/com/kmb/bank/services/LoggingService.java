package com.kmb.bank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoggingService {

    /*@Autowired
    JdbcTemplate jdbcTemplate;*/

    public short queryForUsernameAndReturnColor(String username) {
       /* Map<String, Object> result = jdbcTemplate.queryForMap("some query for now");

        return (short)result.get("color");*/
       return 2;
    }
}
