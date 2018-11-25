package com.kmb.bank.controllers;

import com.kmb.bank.services.TransfersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TransfersController {

    @Autowired
    private TransfersService transfersService;

    @GetMapping(value="/transfer/normal")
    public String getTransfer(HttpServletRequest request, Model model) {
        transfersService.registerAccountNumbers(request, model);
        return "transfer";
    }
}
