package com.kmb.transactionlogger.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class CreditEventDTO {

    @Getter @Setter
    private String accountNumber;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String currency;

    @Getter @Setter
    private double amount;

    @Getter @Setter
    private short currencyId;

    @Getter @Setter
    private short monthsAmount;

    public CreditEventDTO () {
    }
}
