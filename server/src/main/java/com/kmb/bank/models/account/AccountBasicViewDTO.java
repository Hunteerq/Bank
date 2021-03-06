package com.kmb.bank.models.account;

import lombok.Getter;

public class AccountBasicViewDTO {

    @Getter
    private final String number;
    @Getter
    private final double balance;

    private AccountBasicViewDTO(AccountBasicViewDTOBuilder accountBasicViewDTOBuilder) {
        this.number = accountBasicViewDTOBuilder.getNumber();
        this.balance = accountBasicViewDTOBuilder.getBalance();
    }

    public static AccountBasicViewDTOBuilder builder() {
        return new AccountBasicViewDTOBuilder();
    }

    public static class AccountBasicViewDTOBuilder {

        @Getter
        private String number;
        @Getter
        private double balance;

        public AccountBasicViewDTOBuilder setNumber(String number) {
            this.number = number;
            return this;
        }

        public AccountBasicViewDTOBuilder setBalance(double balance) {
            this.balance = balance;
            return this;
        }

        public AccountBasicViewDTO build() {
            return new AccountBasicViewDTO(this);
        }

    }
}
