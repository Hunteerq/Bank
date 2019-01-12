package com.kmb.bank.services;

import com.kmb.bank.db.mongo.repository.AccountRepository;
import com.kmb.bank.db.mongo.repository.CardRepository;
import com.kmb.bank.models.account.AccountBasicViewDTO;
import com.kmb.bank.models.card.CardBasicViedDTO;
import com.kmb.bank.models.card.CardLimitsDTO;
import com.kmb.bank.models.card.CardSpecifiedViewDTO;
import com.kmb.bank.models.card.CardTypeDTO;
import com.kmb.bank.random.NumberGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

    public Boolean addCardToModel(HttpServletRequest request, Model model, String cardNumber) {
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



    public void addNewCard(HttpServletRequest request, String userAccountNumber, int cardTypeId, double dailyContactlessLimit, double dailyWebLimit,  double dailyTotalLimit) {
        String username = Optional.ofNullable((String) request.getSession().getAttribute("username")).orElse("");
        String cardNumber = numberGenerator.returnRandomInts(16);
        String cvv = numberGenerator.returnRandomInts(3);

        if (accountRepository.ifAccountNumberBelongsToUser(userAccountNumber, username)) {
            cardRepository.addNewCreditCard(cardNumber, userAccountNumber, cvv, LocalDateTime.now().plusYears(4),
                    cardTypeId, true,dailyContactlessLimit, dailyTotalLimit, dailyWebLimit);
        }
    }


    public void createEditView(HttpServletRequest request, Model model, String cardNumber) {
        String username = Optional.ofNullable((String) request.getSession().getAttribute("username")).orElse("");
        if(cardRepository.testIfCardBelongsToUsername(username, cardNumber)) {
            Optional<CardLimitsDTO> cardLimitsDTO = Optional.ofNullable(cardRepository.getLimitsFromCreditNumber(cardNumber));
            cardLimitsDTO.ifPresent( limits -> model.addAttribute("cardLimitsDTO", limits));
            model.addAttribute("cardNumber", cardNumber);
        }
    }

    public void updateCardLimits(HttpServletRequest request, String cardNumber, double dailyContactlessLimit, double dailyWebLimit, double dailyTotalLimit) {
        String username = Optional.ofNullable((String) request.getSession().getAttribute("username")).orElse("");
        if(dailyContactlessLimit >= 0 && dailyWebLimit >= 0 && dailyTotalLimit >=0
                && cardRepository.testIfCardBelongsToUsername(username, cardNumber)) {
            cardRepository.updateCardLimits(cardNumber, dailyContactlessLimit, dailyTotalLimit, dailyWebLimit);
        }
    }

    public Boolean createCardDeleteAcceptationView(HttpServletRequest request, Model model, String cardNumber) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent() && cardRepository.testIfCardBelongsToUsername(username.get(), cardNumber)) {
            model.addAttribute("cardNumber", cardNumber);
        } else {
            return false;
        }
        return true;
    }

    public void performCardDelete(HttpServletRequest request, Model model, String cardNumber) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent() && cardRepository.testIfCardBelongsToUsername(username.get(), cardNumber)) {
            cardRepository.deleteCard(cardNumber);
        }
    }
}
