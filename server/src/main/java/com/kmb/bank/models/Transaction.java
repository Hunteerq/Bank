package com.kmb.bank.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
public class Transaction {

    @Getter @Setter
    private long id;
    @Getter @Setter
    private String nadawca;
    @Getter @Setter
    private String senderAccountNumber;
    @Getter @Setter
    private String recipientAccountNumber;
    @Getter @Setter
    private LocalDateTime transactionDate;
    @Getter @Setter
    private double amount;
    @Getter @Setter
    private String title;
    @Getter @Setter
    private Optional<Long> cardId;
//    @Getter @Setter
//    private Address address;
}
