package com.kmb.bank.controllers;

import com.kmb.bank.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@Controller
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping(value = "/card")
    public String getCards(HttpServletRequest request, Model model) {
        cardService.addCardsToModel(request, model);
        return "card";
    }

    @GetMapping(value = "/card/add")
    public String addCardView(HttpServletRequest request, Model model) {
        cardService.addCardTypesAndAccountNumbersToModel(request, model);
        return "card-add.html";
    }

    @PostMapping(value = "/card/add")
    public String addCard(HttpServletRequest request,
                          @PathParam(value = "userAccountNumber") String userAccountNumber,
                          @PathParam(value = "cardType") int cardTypeId,
                          @PathParam(value = "dailyContactlessLimit") double dailyContactlessLimit,
                          @PathParam(value = "dailyWebLimit") double dailyWebLimit,
                          @PathParam(value = "dailyTotalLimit") double dailyTotalLimit) {
        cardService.addNewCard(request, userAccountNumber, cardTypeId, dailyContactlessLimit, dailyWebLimit, dailyTotalLimit);
        return "redirect:/card";
    }

    @GetMapping(value = "/card/{cardNumber}")
    public String getCard(HttpServletRequest request, Model model, @PathVariable String cardNumber) {
        return cardService.addCardToModel(request, model, cardNumber) ? "specified-card" : "redirect:/error";
    }

    @GetMapping(value = "/card/{cardNumber}/block")
    public String blockCard(HttpServletRequest request, @PathVariable String cardNumber) {
        cardService.blockCard(request, cardNumber);
        return "redirect:/card/" + cardNumber;
    }

    @GetMapping(value = "/card/{cardNumber}/unblock")
    public String unblockCard(HttpServletRequest request, @PathVariable String cardNumber) {
        cardService.unblockCard(request, cardNumber);
        return "redirect:/card/" + cardNumber;
    }

    @GetMapping(value="/card/{cardNumber}/edit")
    public String editCard(HttpServletRequest request, Model model,  @PathVariable String cardNumber)  {
        cardService.createEditView(request, model, cardNumber);
        return "card-edit";
    }

    @PostMapping(value="/card/{cardNumber}/edit")
    public String updateCardLimits(HttpServletRequest httpServletRequest,
                                   @PathVariable String cardNumber,
                                   @PathParam(value = "dailyContactlessLimit") double dailyContactlessLimit,
                                   @PathParam(value = "dailyWebLimit") double dailyWebLimit,
                                   @PathParam(value = "dailyTotalLimit") double dailyTotalLimit) {
        cardService.updateCardLimits(httpServletRequest, cardNumber, dailyContactlessLimit, dailyWebLimit, dailyTotalLimit);
        return "redirect:/card/" + cardNumber;
    }

    @GetMapping(value="/card/{cardNumber}/delete")
    public String createCardDeleteAcceptationView(HttpServletRequest httpServletRequest, Model model, @PathVariable String cardNumber) {
        boolean authorization = cardService.createCardDeleteAcceptationView(httpServletRequest, model, cardNumber);
        return authorization ? "card-delete-confirmation" : "redirect:/card";
    }

    @GetMapping(value="/card/{cardNumber}/delete/accepted")
    public String performCardDelete(HttpServletRequest httpServletRequest, Model model, @PathVariable String cardNumber) {
        cardService.performCardDelete(httpServletRequest, model, cardNumber);
        return "redirect:/card";
    }


}
