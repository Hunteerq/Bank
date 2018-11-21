package com.kmb.bank.controllers;

import com.kmb.bank.services.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        Short value = loggingService.validateUsername(username);
        return value!= -200 ? "password" : "login_unsuccessful";
    }


    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String password(HttpServletRequest req,
                           @RequestParam(value = "username", required = false) String username,
                           @RequestParam(value="password", required = false) String password,
                           @RequestParam(value = "error", required = false) String error) {

        return loggingService.validatePassword(username, password) ? "dashboard" :  "password_unsuccessful";
    }


    @RequestMapping("/*")
    public void unknown(HttpServletResponse response)throws Exception{  response.sendRedirect("/"); }

}
