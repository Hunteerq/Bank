package com.kmb.bank.controllers;

import com.kmb.bank.services.TransfersService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
public class TransferController {

    @Autowired
    private TransfersService transfersService;

    @GetMapping(value="/transfer/normal")
    public String getTransfer(HttpServletRequest request, Model model) {
        return transfersService.registerAccountNumbers(request, model)? "transfer" : "redirect:/";
    }

    @PostMapping(value="/transfer/normal")
    public String postTransfer(HttpServletRequest request,
                             @RequestParam(value = "senderAccountNumber") String senderAccountNumber,
                             @RequestParam(value = "title") String title,
                             @RequestParam(value = "recipientName") String recipientName,
                             @RequestParam(value = "recipientAccountNumber") String recipientAccountNumber,
                             @RequestParam(value = "amount") String amount){
        return transfersService.sendNormalTransfer(request, senderAccountNumber, title, recipientName, recipientAccountNumber, amount) ?
                "redirect:/dashboard" : "redirect:/";
    }
}
