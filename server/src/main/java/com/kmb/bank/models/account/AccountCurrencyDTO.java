package com.kmb.bank.models.account;

import lombok.Getter;

import java.util.Optional;

public final class AccountCurrencyDTO {

    @Getter
    private final String number;

    @Getter
    private final String currency;

    @Getter
    private final Optional<String> type;

    @Getter
    private final double balance;

    private AccountCurrencyDTO(AccountDTOBuilder builder) {
        this.number = builder.getNumber();
        this.currency = builder.getCurrency();
        this.type = Optional.ofNullable(builder.getType());
        this.balance = builder.getBalance();
    }

    public static AccountDTOBuilder builder() {
        return new AccountDTOBuilder();
    }

    public static final class AccountDTOBuilder {

        @Getter
        private String number;

        @Getter
        private String currency;

        @Getter
        private String type;

        @Getter
        private double balance;

        public AccountCurrencyDTO build() {
            return new AccountCurrencyDTO(this);
        }

        public AccountDTOBuilder setNumber(String number) {
            this.number = number;
            return this;
        }

        public AccountDTOBuilder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public AccountDTOBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public AccountDTOBuilder setBalance(double balance) {
            this.balance = balance;
            return this;
        }

    }

    @Override
    public String toString() {
        return "[AccountCurrencyDTO: [number=" + number.toString() + "], [balance=" +balance +"]]";
    }

}
