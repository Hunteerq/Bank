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
        /* TODO: userName = cardNumber authorization, returning last card payments
         */

        CardSpecifiedViewDTO cardSpecifiedViewDTO = jdbcTemplate.queryForObject("SELECT card.account_number, card.expiration_date, card_type.type, card.is_active, " +
            "card.daily_contactless_limit, card.daily_total_limit, card.daily_web_limit FROM card " +
            "INNER JOIN card_type ON card_type.id = card.type_id WHERE card.number = ? ",
            new Object[]{cardNumber},
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
    }
}
