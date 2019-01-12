package com.kmb.bank.db.mongo.repository;

import com.kmb.bank.models.account.AccountBasicViewDTO;
import com.kmb.bank.models.card.CardBasicViedDTO;
import com.kmb.bank.models.card.CardLimitsDTO;
import com.kmb.bank.models.card.CardSpecifiedViewDTO;
import com.kmb.bank.models.card.CardTypeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
public class CardRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addNewCreditCard(String cardNumber, String userAccountNumber, String cvv, LocalDateTime plusYears, int cardTypeId, boolean b, double dailyContactlessLimit, double dailyTotalLimit, double dailyWebLimit) {
        try {
            jdbcTemplate.update("INSERT INTO card VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", cardNumber, userAccountNumber,
                    cvv, LocalDateTime.now().plusYears(4), cardTypeId, true,
                    dailyContactlessLimit, dailyTotalLimit, dailyWebLimit);
        } catch(Exception e) {
            log.error("Error updating database {}", e.getMessage());
        }
    }

    public Boolean testIfCardBelongsToUsername(String username, String cardNumber) {
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

    public CardLimitsDTO getLimitsFromCreditNumber(String cardNumber) {
        try {
            CardLimitsDTO cardLimitsDTO = jdbcTemplate.queryForObject("SELECT daily_contactless_limit, daily_total_limit, daily_web_limit FROM card " +
                    "WHERE number = ?", new Object[] {cardNumber},
                    (rs, rowNum) -> CardLimitsDTO.builder()
                            .setDailyContactlessLimit(rs.getDouble("daily_contactless_limit"))
                            .setDailyTotalLimit(rs.getDouble("daily_total_limit"))
                            .setDailyWebLimit(rs.getDouble("daily_web_limit"))
                            .build());
            return cardLimitsDTO;
        } catch(Exception e) {
            log.error("Error asking database for card limits {}", e.getMessage());
            return null;
        }
    }

    public void updateCardLimits(String cardNumber, double dailyContactlessLimit, double dailyTotalLimit, double dailyWebLimit) {
        try {
            jdbcTemplate.update("UPDATE card " +
                            "SET daily_contactless_limit = ?, daily_total_limit = ?, daily_web_limit = ? WHERE number = ?",
                    dailyContactlessLimit, dailyTotalLimit, dailyWebLimit, cardNumber);
        } catch (Exception e) {
            log.error("Error updating card limits {}", e.getMessage());
        }
    }

    public List<CardBasicViedDTO> getCardsWithBasicInformation(String username) {
        List<CardBasicViedDTO> cardBasicViedDTOS = Collections.emptyList();
        try {
            cardBasicViedDTOS = jdbcTemplate.query("SELECT card.number, card_type.type FROM card " +
                            "INNER JOIN account ON account.number = card.account_number " +
                            "INNER JOIN client_account ON client_account.account_number = account.number " +
                            "INNER JOIN client ON client.pesel = client_account.client_pesel AND client.username = ?" +
                            "INNER JOIN card_type ON card_type.id = card.type_id " +
                            "ORDER BY card.number", new Object[]{username},
                    (rs, rowNum) -> CardBasicViedDTO.builder()
                            .setCardNumber(rs.getString("number"))
                            .setCardType(rs.getString("type")).
                                    build());

        } catch(Exception e) {
            log.error("Error getting cards list from database {}", e.getMessage());
        }
        return cardBasicViedDTOS;
    }

    public void deleteCard(String cardNumber) {
        try{
            jdbcTemplate.update("DELETE FROM card WHERE number = ?", cardNumber);
        } catch(Exception e) {
            log.error("Error deleting card number {}, error message {}", cardNumber, e.getMessage());
        }
    }

    public CardSpecifiedViewDTO getDetailedCard(String cardNumber) {
        try {
            return jdbcTemplate.queryForObject("SELECT card.account_number, card.expiration_date, card_type.type, card.is_active, " +
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
        } catch(Exception e) {
           log.error("Error getting card with details {}", e.getMessage());
          return null;
        }
    }

    public List<CardTypeDTO> getCardTypes() {
        try {
            return jdbcTemplate.query("SELECT id, type from card_type",
                    (rs, rowNum) -> CardTypeDTO.builder()
                            .setId(rs.getInt("id"))
                            .setType(rs.getString("type"))
                            .build());
        } catch (Exception e) {
            log.error("Error getting card type in card add view {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void setCardIsActiveTo(String cardNumber, boolean condition) throws Exception{
        jdbcTemplate.update("UPDATE card " +
                "SET is_active = ? " +
                "WHERE number = ?", condition, cardNumber);
    }
}
