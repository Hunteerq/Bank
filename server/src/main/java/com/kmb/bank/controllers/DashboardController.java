package com.kmb.bank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController{

    @GetMapping("/dashboard")
    public String getDashboard() {
        return "dashboard";
    }


    @GetMapping(value="/transfer")
    public String getTransfer() {
        return "transfer";
    }
}
