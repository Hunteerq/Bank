package com.kmb.bank.controllers;

import com.kmb.bank.services.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {

    @Autowired
    private LoggingService loggingService;


    @RequestMapping("/login")
    public String test() {
        return "SIEMANKO PANIE 35";
    }

    @GetMapping("/login")
    public short getLoggin(@RequestParam String username) {
        return loggingService.queryForUsernameAndReturnColor(username);
    }
}
