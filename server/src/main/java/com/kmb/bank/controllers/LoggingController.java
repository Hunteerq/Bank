package com.kmb.bank.controllers;

import com.kmb.bank.services.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class LoggingController {

    @Autowired
    private LoggingService loggingService;

    @GetMapping(value="/")
    public String login(HttpServletRequest request) {
        return loggingService.ifUserLogged(request) ? "redirect:/dashboard" : "login";
    }

    @PostMapping(value="/username")
    public String login(@RequestParam(value = "username") String username, Model model){
        return loggingService.validateUsername(username, model) ? "password" : "login-unsuccessful";
    }

    @PostMapping(value="/password")
    public String password(HttpServletRequest request,
                           Model model,
                           @RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password) {
        return loggingService.validatePassword(request, model, username, password) ? "redirect:/dashboard" : "password-unsuccessful";
    }

    @GetMapping(value="/*")
    public void unknown(HttpServletResponse response)throws Exception{
        response.sendRedirect("/");
    }

}
