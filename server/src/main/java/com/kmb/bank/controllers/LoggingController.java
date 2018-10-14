package com.kmb.bank.controllers;

import com.kmb.bank.services.LoggingService;
import lombok.extern.log4j.Log4j2;
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
        Integer color = loggingService.queryForUsernameAndReturnColor("fmerta");
        return "SIEMANKO PANIE 35, oto Twoj kolor = " + color;
    }

}
