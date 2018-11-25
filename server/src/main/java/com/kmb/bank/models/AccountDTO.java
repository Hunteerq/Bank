package com.kmb.bank.models;

import lombok.Getter;

public final class AccountDTO {

    private AccountDTO(AccountDTOBuilder builder) {
        this.number = builder.getNumber();
        this.balance = builder.getBalance();
    }

    @Getter
    private final String number;
    @Getter
    private final double balance;

    public static AccountDTOBuilder builder() {
        return new AccountDTOBuilder();
    }

    public static final class AccountDTOBuilder {

        @Getter
        private  String number;
        @Getter
        private  double balance;

        public AccountDTO build() {
            return new AccountDTO(this);
        }

        public AccountDTOBuilder setNumber(String number) {
            this.number = number;
            return this;
        }

        public AccountDTOBuilder setBalance(double balance) {
            this.balance = balance;
            return this;
        }

    }

    @Override
    public String toString() {
        return "[AccountDTO: [number=" + number.toString() + "], [balance=" +balance +"]]";
    }

}
