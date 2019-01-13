package com.kmb.transactionlogger.db.mongo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
public class TransferToLogDTO {
    @Getter @Setter
    private String senderName;

    @Getter @Setter
    private String senderAccountNumber;

    @Getter @Setter
    private String recipientName;

    @Getter @Setter
    private String recipientAccountNumber;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private double amount;

    @Getter @Setter
    private double amountInRecipientCurrency;

    @Getter @Setter
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;
}
