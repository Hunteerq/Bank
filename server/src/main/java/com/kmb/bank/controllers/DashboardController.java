package com.kmb.bank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DashboardController{

    @GetMapping("/dashboard")
    public String getDashboard(HttpServletRequest request, Model model) {
        return "dashboard";
    }

}
