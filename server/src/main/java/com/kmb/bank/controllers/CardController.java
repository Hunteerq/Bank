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
        return cardService.addCardsToModel(request, model) ? "card" : "redirect:/login";
    }

    @GetMapping(value = "/card/add")
    public String addCardView(HttpServletRequest request, Model model) {
        return cardService.addCardTypesAndAccountNumbersToModel(request, model) ? "card-add.html" : "redirect:/login";
    }

    @PostMapping(value = "/card/add")
    public String addCard(HttpServletRequest request,
                          @PathParam(value = "userAccountNumber") String userAccountNumber,
                          @PathParam(value = "cardType") int cardTypeId,
                          @PathParam(value = "dailyContactlessLimit") double dailyContactlessLimit,
                          @PathParam(value = "dailyWebLimit") double dailyWebLimit,
                          @PathParam(value = "dailyTotalLimit") double dailyTotalLimit) {
        return cardService.addNewCard(request, userAccountNumber, cardTypeId, dailyContactlessLimit, dailyWebLimit, dailyTotalLimit)
                ? "redirect:/card" : "redirect/login";
    }

    @GetMapping(value = "/card/{cardNumber}")
    public String getCard(HttpServletRequest request, Model model, @PathVariable String cardNumber) {
        return cardService.addCardToModel(request, model, cardNumber) ? "card-specified" : "redirect:/login";
    }

    @GetMapping(value = "/card/{cardNumber}/block")
    public String blockCard(HttpServletRequest request, @PathVariable String cardNumber) {
        return cardService.blockCard(request, cardNumber) ? "redirect:/card/" + cardNumber : "redirect:/login";
    }

    @GetMapping(value = "/card/{cardNumber}/unblock")
    public String unblockCard(HttpServletRequest request, @PathVariable String cardNumber) {
        return cardService.unblockCard(request, cardNumber) ? "redirect:/card/" + cardNumber : "redirect:/login";
    }

    @GetMapping(value="/card/{cardNumber}/edit")
    public String editCard(HttpServletRequest request, Model model,  @PathVariable String cardNumber)  {
        return cardService.createEditView(request, model, cardNumber)? "card-edit" : "redirect:/login";
    }

    @PostMapping(value="/card/{cardNumber}/edit")
    public String updateCardLimits(HttpServletRequest httpServletRequest,
                                   @PathVariable String cardNumber,
                                   @PathParam(value = "dailyContactlessLimit") double dailyContactlessLimit,
                                   @PathParam(value = "dailyWebLimit") double dailyWebLimit,
                                   @PathParam(value = "dailyTotalLimit") double dailyTotalLimit) {
        return cardService.updateCardLimits(httpServletRequest, cardNumber, dailyContactlessLimit, dailyWebLimit, dailyTotalLimit)
                ? "redirect:/card/" + cardNumber : "redirect:/login";
    }

    @GetMapping(value="/card/{cardNumber}/delete")
    public String createCardDeleteAcceptationView(HttpServletRequest httpServletRequest, Model model, @PathVariable String cardNumber) {
        return cardService.createCardDeleteAcceptationView(httpServletRequest, model, cardNumber) ? "card-delete-confirmation" : "redirect:/card";
    }

    @GetMapping(value="/card/{cardNumber}/delete/accepted")
    public String performCardDelete(HttpServletRequest httpServletRequest, Model model, @PathVariable String cardNumber) {
        return cardService.performCardDelete(httpServletRequest, model, cardNumber) ? "redirect:/card" : "redirect:/login";
    }


}
