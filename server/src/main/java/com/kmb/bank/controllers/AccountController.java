package com.kmb.bank.controllers;

import com.kmb.bank.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value="/account")
    public String addAllAccounts(HttpServletRequest httpServletRequest, Model model) {
        return accountService.addAllAccountsToView(httpServletRequest, model) ? "account" : "redirect:/";
    }

    @GetMapping(value="/account/add")
    public String createAccountView(HttpServletRequest httpServletRequest, Model model) {
        return accountService.createAccountView(httpServletRequest, model) ? "account-add" : "redirect:/";
    }

    @PostMapping(value="/account/add")
    public String createAccount(HttpServletRequest request, Model model,
                                @PathParam(value = "accountTypeId") int accountTypeId,
                                @PathParam(value= "currencyId") int currencyId) {
        return accountService.createAccount(request, model, accountTypeId, currencyId) ? "redirect:/account" : "redirect:/";

    }

    @GetMapping(value="/account/{accountNumber}")
    public String createSpecifiedAccountView(HttpServletRequest request, Model model,
                                             @PathVariable String accountNumber) {
        return  accountService.createSpecifiedAccountView(request, model, accountNumber) ? "account-specified" : "redirect:/";
    }

    @GetMapping(value="/account/{accountNumber}/block")
    public String blockSpecifiedAccount(HttpServletRequest request, @PathVariable String accountNumber) {
        return accountService.blockAccount(request, accountNumber) ? "redirect:/account/" + accountNumber : "redirect:/";
    }

    @GetMapping(value="/account/{accountNumber}/unblock")
    public String unblockSpecifiedAccount(HttpServletRequest request, @PathVariable String accountNumber){
        return accountService.unblockAccount(request, accountNumber) ? "redirect:/account/" + accountNumber : "redirect:/";
    }

    @GetMapping(value="/account/{accountNumber}/delete")
    public String deleteSpecifiedAccountView(HttpServletRequest request, Model model, @PathVariable String accountNumber) {
       return accountService.createDeleteView(request, model, accountNumber) ? "account-delete-confirmation" : "redirect:/";
    }

    @GetMapping(value="/account/{accountNumber}/delete/accepted")
    public String deleteSpecifiedAccount(HttpServletRequest request, Model model, @PathVariable String accountNumber) {
        return accountService.deleteAccount(request, accountNumber) ? "redirect:/account" : "redirect:/";
    }


}
