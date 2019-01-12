package com.kmb.bank.db.mongo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Log4j2
@AllArgsConstructor @ToString
public class TransferDTO  {

    @Getter
    private final String senderName;

    @Getter
    private final String senderAccountNumber;

    @Getter
    private final String recipientName;

    @Getter
    private final String recipientAccountNumber;

    @Getter
    private final String title;

    @Getter
    private final double amount;

    @Getter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime localDateTime;

    @Getter @Setter
    private String transferType;
}
