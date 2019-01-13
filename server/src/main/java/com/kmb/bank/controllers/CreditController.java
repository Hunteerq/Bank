package com.kmb.bank.controllers;

import com.kmb.bank.services.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@Controller
public class CreditController {

    @Autowired
    private CreditService creditService;

    @GetMapping(value="/credit")
    public String getAllCredits(HttpServletRequest request, Model model) {
        return creditService.getAllCreditsView(request, model) ? "credit" : "redirect:/login";
    }

    @GetMapping(value="/credit/add")
    public String createAddCreditView(HttpServletRequest request, Model model) {
        return creditService.createAddCreditView(request, model) ? "credit-add" : "redirect:/login";
    }

    @PostMapping(value="/credit/add")
    public String createCredit(HttpServletRequest request, Model model,
                               @PathParam(value = "accountNumber") String accountNumber,
                               @PathParam(value = "currencyId") short currencyId,
                               @PathParam(value = "amount") double amount,
                               @PathParam(value = "monthsAmount") short monthsAmount) {
        return creditService.addCredit(request, model, accountNumber, currencyId, amount, monthsAmount) ? "redirect:/credit" : "login";
    }
}

