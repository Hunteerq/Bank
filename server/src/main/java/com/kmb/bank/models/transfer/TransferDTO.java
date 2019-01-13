package com.kmb.bank.models.transfer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;

import java.time.LocalDateTime;


public final class TransferDTO {

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

    @Getter @JsonSerialize(using = LocalDateTimeSerializer.class)
    private final LocalDateTime localDateTime;

    public static TransferDTOBuilder builder() {
        return new TransferDTOBuilder();
    }

    private TransferDTO(TransferDTOBuilder transferDTOBuilder) {
        this.senderAccountNumber = transferDTOBuilder.getSenderAccountNumber();
        this.title = transferDTOBuilder.getTitle();
        this.recipientName = transferDTOBuilder.getRecipientName();
        this.senderName = transferDTOBuilder.getSenderName();
        this.recipientAccountNumber = transferDTOBuilder.getRecipientAccountNumber();
        this.amount = transferDTOBuilder.getAmount();
        this.localDateTime = transferDTOBuilder.getLocalDateTime();
    }

    public static final class TransferDTOBuilder {

        @Getter
        private String senderName;

        @Getter
        private String senderAccountNumber;

        @Getter
        private String recipientName;

        @Getter
        private String recipientAccountNumber;

        @Getter
        private String title;

        @Getter
        private double amount;

        @Getter
        private LocalDateTime localDateTime;

        public TransferDTO build () {
            return new TransferDTO(this);
        }

        public TransferDTOBuilder setSenderAccountNumber(String senderAccountNumber) {
            this.senderAccountNumber = senderAccountNumber;
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

        public TransferDTOBuilder setSenderName(String senderName) {
            this.senderName = senderName;
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
