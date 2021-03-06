package com.kmb.bank.controllers;

import com.kmb.bank.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DashboardController{

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String getDashboard(HttpServletRequest request, Model model) {
        return dashboardService.prepareDashboardView(request, model) ? "dashboard" : "redirect:/";
    }

    @GetMapping("/contact")
    public String getContact(HttpServletRequest request) {
        return dashboardService.ifUserIsLogged(request) ? "contact" : "redirect:/";
    }


}
