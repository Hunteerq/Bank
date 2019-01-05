package com.kmb.bank.models;

import lombok.Getter;

import java.time.LocalDate;

public class CardSpecifiedViewDTO {

    @Getter
    private final String cardNumber;

    @Getter
    private final String cardAccountNumber;

    @Getter
    private final LocalDate expirationDate;

    @Getter
    private final String cardType;

    @Getter
    private final boolean isActive;

    @Getter
    private final double dailyContactlessLimit;

    @Getter
    private final double dailyTotalLimit;

    @Getter
    private final double dailyWebLimit;

    private CardSpecifiedViewDTO (CardSpecifiedViewDTOBuilder builder) {
        this.cardNumber = builder.getCardNumber();
        this.cardAccountNumber = builder.getCardAccountNumber();
        this.expirationDate = builder.getExpirationDate();
        this.cardType = builder.getCardType();
        this.isActive = builder.isActive();
        this.dailyContactlessLimit = builder.getDailyContactlessLimit();
        this.dailyTotalLimit = builder.getDailyTotalLimit();
        this.dailyWebLimit = builder.getDailyWebLimit();
    }

    public static CardSpecifiedViewDTOBuilder builder() {
        return new CardSpecifiedViewDTOBuilder();
    }

    public static class CardSpecifiedViewDTOBuilder {

        @Getter
        private  String cardNumber;

        @Getter
        private  String cardAccountNumber;

        @Getter
        private  LocalDate expirationDate;

        @Getter
        private  String cardType;

        @Getter
        private boolean isActive;

        @Getter
        private  double dailyContactlessLimit;

        @Getter
        private  double dailyTotalLimit;

        @Getter
        private  double dailyWebLimit;

        public CardSpecifiedViewDTOBuilder setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public CardSpecifiedViewDTOBuilder setCardAccountNumber(String cardAccountNumber) {
            this.cardAccountNumber = cardAccountNumber;
            return this;
        }

        public CardSpecifiedViewDTOBuilder setExpirationDate(LocalDate expiration_date) {
            this.expirationDate = expiration_date;
            return this;
        }

        public CardSpecifiedViewDTOBuilder setCardType(String cardType) {
            this.cardType = cardType;
            return this;
        }

        public CardSpecifiedViewDTOBuilder setActive(boolean active) {
            isActive = active;
            return this;
        }

        public CardSpecifiedViewDTOBuilder setDailyContactlessLimit(double dailyContactlessLimit) {
            this.dailyContactlessLimit = dailyContactlessLimit;
            return this;
        }

        public CardSpecifiedViewDTOBuilder setDailyTotalLimit(double dailyTotalLimit) {
            this.dailyTotalLimit = dailyTotalLimit;
            return this;
        }

        public CardSpecifiedViewDTOBuilder setDailyWebLimit(double dailyWebLimit) {
            this.dailyWebLimit = dailyWebLimit;
            return this;
        }

        public CardSpecifiedViewDTO build() {
            return new CardSpecifiedViewDTO(this);
        }
    }
}
