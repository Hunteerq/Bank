package com.kmb.bank.models.credit;

import lombok.Getter;

public class CreditEventDTO {

    @Getter
    private final String accountNumber;

    @Getter
    private final String username;

    @Getter
    private final String currency;

    @Getter
    private final double amount;

    @Getter
    private final short currencyId;

    @Getter
    private final short monthsAmount;

    public static CreditEventDTOBuilder builder() {
        return new CreditEventDTOBuilder();
    }

    private CreditEventDTO(CreditEventDTOBuilder builder) {
        this.accountNumber = builder.getAccountNumber();
        this.username = builder.getUsername();
        this.currency = builder.getCurrency();
        this.amount = builder.getAmount();
        this.currencyId = builder.getCurrencyId();
        this.monthsAmount = builder.getMonthsAmount();
    }

    public static final class CreditEventDTOBuilder {

        @Getter
        private String accountNumber;

        @Getter
        private String username;

        @Getter
        private String currency;

        @Getter
        private double amount;

        @Getter
        private short currencyId;

        @Getter
        private short monthsAmount;

        public CreditEventDTOBuilder setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public CreditEventDTOBuilder setUsername(String username) {
            this.username = username;
            return this;
        }


        public CreditEventDTOBuilder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public CreditEventDTOBuilder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public CreditEventDTOBuilder setCurrencyId(short currencyId) {
            this.currencyId = currencyId;
            return this;
        }

        public CreditEventDTOBuilder setMonthsAmount(short monthsAmount) {
            this.monthsAmount = monthsAmount;
            return this;
        }

        public CreditEventDTO build() {
            return new CreditEventDTO(this);
        }

    }
}
