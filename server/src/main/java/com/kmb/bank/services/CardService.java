package com.kmb.bank.services;

import com.kmb.bank.db.mongo.repository.AccountRepository;
import com.kmb.bank.db.mongo.repository.CardRepository;
import com.kmb.bank.db.mongo.repository.MongoTransactionRepository;
import com.kmb.bank.models.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@Log4j2
@Service
public class CardService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MongoTransactionRepository mongoTransactionRepository;

    @Autowired
    private Random random;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardRepository cardRepository;

    public void addCardsToModel(HttpServletRequest request, Model model) {
        String username = Optional.ofNullable((String) request.getSession().getAttribute("username")).orElse("");

        List<CardBasicViedDTO> cardDTOS = jdbcTemplate.query("SELECT card.number, card_type.type FROM card " +
               "INNER JOIN account ON account.number = card.account_number " +
               "INNER JOIN client_account ON client_account.account_number = account.number " +
               "INNER JOIN client ON client.pesel = client_account.client_pesel AND client.username = ?" +
               "INNER JOIN card_type ON card_type.id = card.type_id ", new Object[]{username},
               (rs, rowNum) -> CardBasicViedDTO.builder()
                                .setCardNumber(rs.getString("number"))
                                .setCardType(rs.getString("type")).
                               build());

        model.addAttribute("cardDTOS", cardDTOS);
    }

    public Boolean addCardToModel(HttpServletRequest request, Model model, String cardNumber) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        return username.isPresent() ? requestCardAuthorization(request, model, username.get(), cardNumber) : false;
    }

    private Boolean requestCardAuthorization(HttpServletRequest request, Model model, String username, String cardNumber) {
        return cardRepository.testIfCardBelongsToUsername(username, cardNumber) ? addSpecifiedCardToView(request, model, cardNumber) : false;
    }


    private Boolean addSpecifiedCardToView(HttpServletRequest request, Model model, String cardNumber) {
        try {
            CardSpecifiedViewDTO cardSpecifiedViewDTO = jdbcTemplate.queryForObject("SELECT card.account_number, card.expiration_date, card_type.type, card.is_active, " +
                            "card.daily_contactless_limit, card.daily_total_limit, card.daily_web_limit FROM card " +
                            "INNER JOIN card_type ON card_type.id = card.type_id WHERE card.number = ? ", new Object[]{cardNumber},
                    (rs, rowNum) -> CardSpecifiedViewDTO.builder()
                            .setCardNumber(cardNumber)
                            .setCardAccountNumber(rs.getString("account_number"))
                            .setExpirationDate((rs.getDate("expiration_date")).toLocalDate())
                            .setCardType(rs.getString("type"))
                            .setActive(rs.getBoolean("is_active"))
                            .setDailyContactlessLimit(rs.getDouble("daily_contactless_limit"))
                            .setDailyTotalLimit(rs.getDouble("daily_total_limit"))
                            .setDailyWebLimit(rs.getDouble("daily_web_limit"))
                            .build());
            model.addAttribute("cardSpecifiedViewDTO", cardSpecifiedViewDTO);
            return true;
        } catch (Exception e) {
            log.error("Error occured while getting specified card details: {}", e.getMessage());
            return false;
        }
    }


    public void blockCard(HttpServletRequest request, String cardNumber) {
        String username = Optional.ofNullable((String) request.getSession().getAttribute("username")).orElse("");
       if(cardRepository.testIfCardBelongsToUsername(username, cardNumber)) {
           try {
               jdbcTemplate.update("UPDATE card " +
                       "SET is_active = false " +
                       "WHERE number = ?", cardNumber);
           } catch (Exception e ) {
               log.error("Error updating database {}", e.getMessage());
           }
       }
    }

    public void unblockCard(HttpServletRequest request, String cardNumber) {
        String username = Optional.ofNullable((String) request.getSession().getAttribute("username")).orElse("");
        if(cardRepository.testIfCardBelongsToUsername(username, cardNumber)) {
            try {
                jdbcTemplate.update("UPDATE card " +
                        "SET is_active = true " +
                     "WHERE number = ?", cardNumber);
            } catch (Exception e ) {
                 log.error("Error updating database {}", e.getMessage());
             }

          }
    }

    public void addCardTypesAndAccountNumbersToModel(HttpServletRequest request, Model model) {
        String username = Optional.ofNullable((String) request.getSession().getAttribute("username")).orElse("");
        log.info("username = {}", username);

        try {
            Optional<List<AccountBasicViewDTO>> accountBasicViewDTOS = Optional.ofNullable(jdbcTemplate.query("SELECT account.number, account.balance FROM account " +
                    "INNER JOIN client_account on account.number = client_account.account_number " +
                    "INNER JOIN client on client_account.client_pesel = client.pesel " +
                    "WHERE client.username = ?", new Object[]{username}, (rs, rowNum) -> AccountBasicViewDTO.builder()
                    .setAmount(rs.getString("balance"))
                    .setNumber(rs.getString("number"))
                    .build()));
            accountBasicViewDTOS.ifPresent(accounts -> model.addAttribute("accountBasicViewDTOS", accounts));

            Optional<List<CardTypeDTO>> cardTypes = Optional.ofNullable(jdbcTemplate.query("SELECT id, type from card_type",
                    (rs, rowNum) -> CardTypeDTO.builder()
                                                .setId(rs.getInt("id"))
                                                .setType(rs.getString("type"))
                                                .build()));
            cardTypes.ifPresent(types -> model.addAttribute("cardTypeDTOS", types));
        } catch (Exception e) {
            log.info("Exception {}", e.getMessage());
        }
    }



    public void addNewCard(HttpServletRequest request, String userAccountNumber, int cardTypeId, double dailyContactlessLimit, double dailyWebLimit,  double dailyTotalLimit) {
        random.setSeed(System.currentTimeMillis());

        String username = Optional.ofNullable((String) request.getSession().getAttribute("username")).orElse("");
        String cardNumber = returnRandomInts(16);
        String cvv = returnRandomInts(3);

        if (accountRepository.ifAccountNumberBelongsToUser(userAccountNumber, username)) {
            cardRepository.addNewCreditCard(cardNumber, userAccountNumber, cvv, LocalDateTime.now().plusYears(4),
                    cardTypeId, true,dailyContactlessLimit, dailyTotalLimit, dailyWebLimit);
        }
    }

    private String returnRandomInts(int numberOfInts) {
        StringBuilder randomString = new StringBuilder();
        for(int i = 0; i < numberOfInts; i++) {
            randomString.append(random.nextInt(10));
        }
        return randomString.toString();
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
        if(cardRepository.testIfCardBelongsToUsername(username, cardNumber)) {
            cardRepository.updateCardLimits(cardNumber, dailyContactlessLimit, dailyTotalLimit, dailyWebLimit);
        }
    }
}
