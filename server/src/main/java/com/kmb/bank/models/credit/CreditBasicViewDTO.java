package com.kmb.bank.models.credit;

import lombok.Getter;

import java.util.Date;

public class CreditBasicViewDTO {

    @Getter
    private final long id;

    @Getter
    private final double amount;

    @Getter
    private final String currency;

    @Getter
    private final short monthsAmount;

    @Getter
    private final Date startDate;

    public static CreditBasicViewDTOBuilder builder() {
        return new CreditBasicViewDTOBuilder();
    }

    private CreditBasicViewDTO(CreditBasicViewDTOBuilder builder) {
        this.id = builder.getId();
        this.amount = builder.getAmount();
        this.currency = builder.getCurrency();
        this.monthsAmount = builder.getMonthsAmount();
        this.startDate = builder.getStartDate();
    }

    public final static class CreditBasicViewDTOBuilder {

        @Getter
        private long id;

        @Getter
        private double amount;

        @Getter
        private String currency;

        @Getter
        private short monthsAmount;

        @Getter
        private Date startDate;

        public CreditBasicViewDTOBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public CreditBasicViewDTOBuilder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public CreditBasicViewDTOBuilder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public CreditBasicViewDTOBuilder setMonthsAmount(short monthsAmount) {
            this.monthsAmount = monthsAmount;
            return this;
        }

        public CreditBasicViewDTOBuilder setStartDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public CreditBasicViewDTO build() {
            return new CreditBasicViewDTO(this);
        }
    }
}
