package com.kmb.bank.db.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Log4j2
@AllArgsConstructor @ToString
public class TransferDTO implements  Comparable<TransferDTO> {
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
    private final LocalDateTime localDateTime;
    @Getter @Setter
    private String transferType;

    @Override
    public int compareTo(TransferDTO o) {
        long subtraction = this.localDateTime.toEpochSecond(ZoneOffset.UTC) - o.localDateTime.toEpochSecond(ZoneOffset.UTC);
        if (subtraction > 0) {
            return -1;
        } else if (subtraction == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
