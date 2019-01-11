package com.kmb.bank.models.card;

import lombok.Getter;

public class CardLimitsDTO {

    @Getter
    private final double dailyContactlessLimit;

    @Getter
    private final double dailyTotalLimit;

    @Getter
    private final double dailyWebLimit;

    public static CardLimitsDTOBuilder builder() {
        return new CardLimitsDTOBuilder();
    }

    private CardLimitsDTO(CardLimitsDTOBuilder cardLimitsDTOBuilder) {
        this.dailyContactlessLimit = cardLimitsDTOBuilder.getDailyContactlessLimit();
        this.dailyTotalLimit = cardLimitsDTOBuilder.getDailyTotalLimit();
        this.dailyWebLimit =  cardLimitsDTOBuilder.getDailyWebLimit();
    }

    public static final class CardLimitsDTOBuilder {

        @Getter
        private  double dailyContactlessLimit;

        @Getter
        private  double dailyTotalLimit;

        @Getter
        private  double dailyWebLimit;


        public CardLimitsDTOBuilder setDailyContactlessLimit(double dailyContactlessLimit) {
            this.dailyContactlessLimit = dailyContactlessLimit;
            return this;
        }

        public CardLimitsDTOBuilder setDailyTotalLimit(double dailyTotalLimit) {
            this.dailyTotalLimit = dailyTotalLimit;
            return this;
        }

        public CardLimitsDTOBuilder setDailyWebLimit(double dailyWebLimit) {
            this.dailyWebLimit = dailyWebLimit;
            return this;
        }

        public CardLimitsDTO build() {
            return new CardLimitsDTO(this);
        }

    }
}
