package com.kmb.bank.controllers;

import com.kmb.bank.models.Address;
import com.kmb.bank.models.Transaction;
import com.kmb.bank.services.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class LoggingController {

    @Autowired
    private LoggingService loggingService;

    @RequestMapping("/login")
    public String test() {
        Integer color = loggingService.queryForUsernameAndReturnColor("fmerta");
        return "SIEMANKO PANIE 35, oto Twoj kolor = " + color;
    }

    @RequestMapping("/testRabbit")
    public void testRabbit() {
        Address address = new Address(1, "Podchorazych", 15, 6, "30-711", "Poland");
        Transaction transaction = new Transaction(1, "666223452626", "15523632673470", LocalDateTime.now(), 421.53, "Za Merte", 1, address );
        loggingService.testRabbitMq(transaction);

    }

}
