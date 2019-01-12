package com.kmb.bank.models.account;

import lombok.Getter;

import java.util.Date;

public class AccountSpecifiedViewDTO {

    @Getter
    private final String number;

    @Getter
    private final double balance;

    @Getter
    private final String currency;

    @Getter
    private final String type;

    @Getter
    private final Date openingDate;

    @Getter
    private final boolean active;

    private AccountSpecifiedViewDTO(AccountSpecifiedViewDTOBuilder builder) {
        this.number = builder.getNumber();
        this.balance = builder.getBalance();
        this.currency = builder.getCurrency();
        this.type = builder.getType();
        this.openingDate = builder.getOpeningDate();
        this.active = builder.isActive();
    }

    public static AccountSpecifiedViewDTOBuilder builder(){
        return new AccountSpecifiedViewDTOBuilder();
    }

    public final static class AccountSpecifiedViewDTOBuilder {

        @Getter
        private  String number;

        @Getter
        private  double balance;

        @Getter
        private  String currency;

        @Getter
        private  String type;

        @Getter
        private  Date openingDate;

        @Getter
        private  boolean active;

        public AccountSpecifiedViewDTOBuilder setNumber(String number) {
            this.number = number;
            return this;
        }

        public AccountSpecifiedViewDTOBuilder setBalance(double balance) {
            this.balance = balance;
            return this;
        }

        public AccountSpecifiedViewDTOBuilder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public AccountSpecifiedViewDTOBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public AccountSpecifiedViewDTOBuilder setOpeningDate(Date openingDate) {
            this.openingDate = openingDate;
            return this;
        }

        public AccountSpecifiedViewDTOBuilder setActive(boolean active) {
            this.active = active;
            return this;
        }

        public AccountSpecifiedViewDTO build() {
            return new AccountSpecifiedViewDTO(this);
        }
    }
}
