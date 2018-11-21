package com.kmb.bank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DashboardController{

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public String getTransfer() {
        return "transfer";
    }
}
