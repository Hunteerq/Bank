package com.kmb.bank.services;

import com.kmb.bank.db.postgres.repository.AccountRepository;
import com.kmb.bank.db.postgres.repository.CardRepository;
import com.kmb.bank.models.account.AccountBasicViewDTO;
import com.kmb.bank.models.card.CardBasicViedDTO;
import com.kmb.bank.models.card.CardLimitsDTO;
import com.kmb.bank.models.card.CardSpecifiedViewDTO;
import com.kmb.bank.models.card.CardTypeDTO;
import com.kmb.bank.random.NumberGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class CardService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private NumberGenerator numberGenerator;

    public boolean addCardsToModel(HttpServletRequest request, Model model) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent()) {
            List<CardBasicViedDTO> cardDTOS = cardRepository.getCardsWithBasicInformation(username.get());
            model.addAttribute("cardDTOS", cardDTOS);
            return true;
        }
        return false;
    }

    public boolean addCardToModel(HttpServletRequest request, Model model, String cardNumber) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent() && cardRepository.testIfCardBelongsToUsername(username.get(), cardNumber)) {
            try {
                Optional<CardSpecifiedViewDTO> cardSpecifiedViewDTO = Optional.ofNullable(cardRepository.getDetailedCard(cardNumber));
                cardSpecifiedViewDTO.ifPresent(card -> model.addAttribute("cardSpecifiedViewDTO", card));
                return true;
            } catch (Exception e) {
                log.error("Error occured while getting specified card details: {}", e.getMessage());
            }
        }
        return false;
    }

    public boolean blockCard(HttpServletRequest request, String cardNumber) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
       if(username.isPresent() && cardRepository.testIfCardBelongsToUsername(username.get(), cardNumber)) {
           try {
               cardRepository.setCardIsActiveTo(cardNumber, false);
               return true;
           } catch (Exception e ) {
               log.error("Error updating database {}", e.getMessage());
           }
       }
       return false;
    }

    public boolean unblockCard(HttpServletRequest request, String cardNumber) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent() && cardRepository.testIfCardBelongsToUsername(username.get(), cardNumber)) {
            try {
                cardRepository.setCardIsActiveTo(cardNumber, true);
                return true;
            } catch (Exception e ) {
                 log.error("Error updating database {}", e.getMessage());
             }
        }
        return false;
    }

    public boolean addCardTypesAndAccountNumbersToModel(HttpServletRequest request, Model model) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent()) {
            List<AccountBasicViewDTO> accountBasicViewDTOS = accountRepository.getAccountNumbers(username.get());
            List<CardTypeDTO> cardTypes = cardRepository.getCardTypes();
            model.addAttribute("accountBasicViewDTOS", accountBasicViewDTOS);
            model.addAttribute("cardTypeDTOS", cardTypes);
            return true;
        }
        return false;
    }



    public boolean addNewCard(HttpServletRequest request, String userAccountNumber, int cardTypeId, double dailyContactlessLimit, double dailyWebLimit,  double dailyTotalLimit) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        String cardNumber = numberGenerator.returnRandomInts(16);
        String cvv = numberGenerator.returnRandomInts(3);
        if (username.isPresent() && accountRepository.ifAccountNumberBelongsToUser(userAccountNumber, username.get())) {
            cardRepository.addNewCreditCard(cardNumber, userAccountNumber, cvv, LocalDateTime.now().plusYears(4),
                    cardTypeId, true,dailyContactlessLimit, dailyTotalLimit, dailyWebLimit);
            return true;
        }
        return false;
    }


    public boolean createEditView(HttpServletRequest request, Model model, String cardNumber) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent() && cardRepository.testIfCardBelongsToUsername(username.get(), cardNumber)) {
            Optional<CardLimitsDTO> cardLimitsDTO = Optional.ofNullable(cardRepository.getLimitsFromCreditNumber(cardNumber));
            cardLimitsDTO.ifPresent( limits -> model.addAttribute("cardLimitsDTO", limits));
            model.addAttribute("cardNumber", cardNumber);
            return true;
        }
        return false;
    }

    public boolean updateCardLimits(HttpServletRequest request, String cardNumber, double dailyContactlessLimit, double dailyWebLimit, double dailyTotalLimit) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent() && dailyContactlessLimit >= 0 && dailyWebLimit >= 0 && dailyTotalLimit >=0
                && cardRepository.testIfCardBelongsToUsername(username.get(), cardNumber)) {
            cardRepository.updateCardLimits(cardNumber, dailyContactlessLimit, dailyTotalLimit, dailyWebLimit);
            return true;
        }
        return false;
    }

    public boolean createCardDeleteAcceptationView(HttpServletRequest request, Model model, String cardNumber) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent() && cardRepository.testIfCardBelongsToUsername(username.get(), cardNumber)) {
            model.addAttribute("cardNumber", cardNumber);
        } else {
            return false;
        }
        return true;
    }

    public boolean performCardDelete(HttpServletRequest request, Model model, String cardNumber) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent() && cardRepository.testIfCardBelongsToUsername(username.get(), cardNumber)) {
            cardRepository.deleteCard(cardNumber);
            return true;
        }
        return false;
    }
}
