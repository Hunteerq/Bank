package com.kmb.bank.db.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor @ToString
public class TransferDTO {
    @Getter
    private final String userAccountNumber;
    @Getter
    private final String title;
    @Getter
    private final String recipientName;
    @Getter
    private final String recipientAccountNumber;
    @Getter
    private final double amount;
    @Getter
    private LocalDateTime localDateTime;
}
