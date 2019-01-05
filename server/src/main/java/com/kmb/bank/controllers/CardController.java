package com.kmb.bank.controllers;

import com.kmb.bank.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping(value = "/card")
    public String getCards(HttpServletRequest request, Model model) {
        cardService.addCardsToModel(request, model);
        return "card";
    }

    @GetMapping(value = "/card/{cardNumber}")
    public String getCard(HttpServletRequest request, Model model, @PathVariable String cardNumber) {
       return cardService.addCardToModel(request, model, cardNumber)  ? "specifiedCard" : "errorPage";
    }
}
