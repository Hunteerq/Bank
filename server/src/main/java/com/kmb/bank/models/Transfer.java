package com.kmb.bank.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;

import java.time.LocalDateTime;


public final class Transfer {

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
    @Getter @JsonSerialize(using = LocalDateTimeSerializer.class)
    private final LocalDateTime localDateTime;

    public static TransferDTOBuilder builder() {
        return new TransferDTOBuilder();
    }

    private Transfer(TransferDTOBuilder transferDTOBuilder) {
        this.userAccountNumber = transferDTOBuilder.getUserAccountNumber();
        this.title = transferDTOBuilder.getTitle();
        this.recipientName = transferDTOBuilder.getRecipientName();
        this.recipientAccountNumber = transferDTOBuilder.getRecipientAccountNumber();
        this.amount = transferDTOBuilder.getAmount();
        this.localDateTime = transferDTOBuilder.getLocalDateTime();
    }

    public static final class TransferDTOBuilder {

        @Getter
        private String userAccountNumber;
        @Getter
        private String title;
        @Getter
        private String recipientName;
        @Getter
        private String recipientAccountNumber;
        @Getter
        private double amount;
        @Getter
        private LocalDateTime localDateTime;

        public Transfer build () {
            return new Transfer(this);
        }

        public TransferDTOBuilder setUserAccountNumber(String userAccountNumber) {
            this.userAccountNumber = userAccountNumber;
            return this;
        }

        public TransferDTOBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public TransferDTOBuilder setRecipientName(String recipientName) {
            this.recipientName = recipientName;
            return this;
        }

        public TransferDTOBuilder setRecipientAccountNumber(String recipientAccountNumber) {
            this.recipientAccountNumber = recipientAccountNumber;
            return this;
        }

        public TransferDTOBuilder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public TransferDTOBuilder setLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
            return this;
        }

    }
}
