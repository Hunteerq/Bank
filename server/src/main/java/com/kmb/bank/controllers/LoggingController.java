package com.kmb.bank.controllers;

import com.kmb.bank.services.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class LoggingController {

    @Autowired
    private LoggingService loggingService;

    @GetMapping(value="/")
    public String login() {
        return "login";
    }

    @PostMapping(value="/username")
    public String login(@RequestParam(value = "username") String username){
        return loggingService.validateUsername(username)!= -1 ? "password" : "login_unsuccessful";
    }

    @PostMapping(value="/password")
    public String password(@RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           HttpServletRequest request) {
        return loggingService.validatePassword(request, username, password) ? "redirect:/dashboard" : "password_unsuccessful";
    }

    @GetMapping(value="/*")
    public void unknown(HttpServletResponse response)throws Exception{
        response.sendRedirect("/");
    }

}
