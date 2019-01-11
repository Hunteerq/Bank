package com.kmb.bank.services;


import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Log4j2
@Service
public class LoggingService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Boolean validateUsername(String username, Model model) {
        try {
            Short color = jdbcTemplate.queryForObject("SELECT login.color FROM login " +
                    "WHERE login.username = ?", new Object[] {username}, Short.class);

            model.addAttribute("userColor", color);
            return true;
        } catch (DataAccessException E) {
            log.debug("Error, too many users with the same username " + E.getMessage());
            return false;
        }

    }

    public boolean validatePassword(HttpServletRequest request, String username, String password) {
        try {
            String encodedPassword = DigestUtils.md5Hex(password);
            String ifValidated =  jdbcTemplate.queryForObject("SELECT login.username FROM login " +
                    "WHERE login.username = ? AND login.password = ?", new Object[] {username, encodedPassword},  String.class);
            saveSession(request, username, password);
            return true;
        } catch (DataAccessException E) {
            log.info("Not validated " + E.getMessage());
            return false;
        }
    }

    public void saveSession(HttpServletRequest request, String username, String password) {
        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("nameSurname", getNameFromUsername(username));
    }

    public String getNameFromUsername(String username) {
        String name = jdbcTemplate.queryForObject("SELECT CONCAT(name, ' ',surname) FROM client " +
                "WHERE username = ?", new Object[] {username}, String.class);
        log.info("Name+ surname = " + name);
        return name;
    }


    public boolean ifUserLogged(HttpServletRequest request) {
        return Optional.ofNullable(request.getSession().getAttribute("username")).isPresent();
    }
}
