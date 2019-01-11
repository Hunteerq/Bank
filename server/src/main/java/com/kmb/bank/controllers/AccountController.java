package com.kmb.bank.controllers;

import com.kmb.bank.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value="/account")
    public String addAllAccounts(HttpServletRequest httpServletRequest, Model model) {
        return accountService.addAllAccountsToView(httpServletRequest, model) ? "account" : "redirect:/login";
    }

    @GetMapping(value="/account/add")
    public String createAccountView(HttpServletRequest httpServletRequest, Model model) {
        return accountService.createAccountView(httpServletRequest, model) ? "account-add" : "redirect:/login";
    }

    @PostMapping(value="/account/add")
    public String createAccount(HttpServletRequest request, Model model,
                                @PathParam(value = "accountTypeId") int accountTypeId,
                                @PathParam(value= "currencyId") int currencyId) {
        return accountService.createAccount(request, model, accountTypeId, currencyId) ? "redirect:/account" : "redirect:/login";

    }

}
