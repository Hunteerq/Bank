package com.kmb.bank.controllers;

import com.kmb.bank.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value="/account")
    public String addAllAccounts(HttpServletRequest httpServletRequest, Model model) {
        return accountService.addAllAccountsToView(httpServletRequest, model) ? "account" : "redirect:/login";
    }

    @GetMapping(value="/account/add")
    public String createAccount(HttpServletRequest httpServletRequest, Model model) {
        return accountService.createAccount(httpServletRequest, model) ? "account-add" : "redirect:/login";
    }

}
