package com.kmb.transactionlogger.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor @ToString
public class TransferDTO {

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
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    public TransferDTO() { }

}
