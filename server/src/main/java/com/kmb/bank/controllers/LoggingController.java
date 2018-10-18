package com.kmb.bank.controllers;

import com.kmb.bank.services.LoggingService;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
public class LoggingController {

    @Autowired
    private LoggingService loggingService;


    @RequestMapping("/")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/username" , method= RequestMethod.POST)
    public String login(HttpServletRequest req,
                        @RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "error", required = false) String error){

        Integer value = loggingService.validateUsername(username);
        if (value!= -200) {
            return "password";
        } else {
            return "login_unsuccessful";
        }
    }


    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String password(HttpServletRequest req,
                           @RequestParam(value = "username", required = false) String username,
                           @RequestParam(value="password", required = false) String password,
                           @RequestParam(value = "error", required = false) String error) {

        if (loggingService.validatePassword(username, password)) {
            return "dashboard";
        } else {
            return "password_unsuccessful";
        }
    }


}
