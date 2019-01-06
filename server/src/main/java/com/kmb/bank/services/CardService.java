package com.kmb.bank.services;

import com.kmb.bank.db.mongo.repository.MongoTransactionRepository;
import com.kmb.bank.models.CardBasicViedDTO;
import com.kmb.bank.models.CardSpecifiedViewDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Log4j2
@Service
public class CardService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MongoTransactionRepository mongoTransactionRepository;

    public void addCardsToModel(HttpServletRequest request, Model model) {
       String username = (String)request.getSession().getAttribute("username");

        List<CardBasicViedDTO> cardDTOS = jdbcTemplate.query("SELECT card.number, card_type.type FROM card " +
               "INNER JOIN account ON account.number = card.account_number " +
               "INNER JOIN client_account ON client_account.account_number = account.number " +
               "INNER JOIN client ON client.pesel = client_account.client_pesel AND client.username = ?" +
               "INNER JOIN card_type ON card_type.id = card.type_id ", new Object[]{username},
               (rs, rowNum) -> CardBasicViedDTO.builder()
                                .setCardNumber(rs.getString("number"))
                                .setCardType(rs.getString("type")).
                               build()).stream()
                .collect(Collectors.toList());

        model.addAttribute("cardDTOS", cardDTOS);
    }

    public Boolean addCardToModel(HttpServletRequest request, Model model, String cardNumber) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        return username.isPresent() ? requestCardAuthorization(request, model, username.get(), cardNumber) : false;
    }

    private Boolean requestCardAuthorization(HttpServletRequest request, Model model, String username, String cardNumber) {
        return testIfCardBelongsToUsername(username, cardNumber) ? addSpecifiedCardToView(request, model, cardNumber) : false;
    }

    private Boolean testIfCardBelongsToUsername(String username, String cardNumber) {
        try {
            Integer numbers = jdbcTemplate.queryForObject("SELECT count(*) FROM card " +
                    "INNER JOIN account ON account.number = card.account_number " +
                    "INNER JOIN client_account ON client_account.account_number = account.number " +
                    "INNER JOIN client ON client.pesel = client_account.client_pesel AND client.username = ? " +
                    "WHERE card.number = ?", new Object[] {username, cardNumber}, Integer.class);
            return numbers > 0;
        } catch (Exception e ) {
            log.error("Error while testing if card belongs to username {}", e.getMessage());
            return false;
        }
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
       Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
       if(username.isPresent() && testIfCardBelongsToUsername(username.get(), cardNumber)) {
           jdbcTemplate.update("UPDATE card " +
                   "SET is_active = false " +
                   "WHERE number = ?", new Object[] {cardNumber});
       }
    }

    public void unblockCard(HttpServletRequest request, String cardNumber) {
        String username = (String) request.getSession().getAttribute("username");
        if(username != null && testIfCardBelongsToUsername(username, cardNumber)) {
            jdbcTemplate.update("UPDATE card " +
                    "SET is_active = true " +
                    "WHERE number = ?", new Object[] {cardNumber});
        }
    }
}
