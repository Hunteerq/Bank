package com.kmb.bank.models;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TransferViewDTO implements Comparable<TransferViewDTO> {

    @Getter
    private final String name;

    @Getter
    private final String accountNumber;

    @Getter
    private final String title;

    @Getter
    private final double amount;

    @Getter
    private final String transferType;

    @Getter
    private final LocalDateTime localDateTime;

    public static TransferDTOBuilder builder() {
        return new TransferDTOBuilder();
    }

    @Override
    public int compareTo(TransferViewDTO o) {
        long subtraction = this.localDateTime.toEpochSecond(ZoneOffset.UTC) - o.localDateTime.toEpochSecond(ZoneOffset.UTC);
        if (subtraction > 0) {
            return -1;
        } else if (subtraction == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    private TransferViewDTO(TransferDTOBuilder transferDTOBuilder) {
        this.accountNumber = transferDTOBuilder.getAccountNumber();
        this.title = transferDTOBuilder.getTitle();
        this.name = transferDTOBuilder.getName();
        this.transferType = transferDTOBuilder.getTransferType();
        this.amount = transferDTOBuilder.getAmount();
        this.localDateTime = transferDTOBuilder.getLocalDateTime();
    }

    public static final class TransferDTOBuilder {

        @Getter
        private String name;

        @Getter
        private String accountNumber;

        @Getter
        private String title;

        @Getter
        private double amount;

        @Getter
        private String transferType;

        @Getter
        private LocalDateTime localDateTime;

        public TransferViewDTO build () {
            return new TransferViewDTO(this);
        }

        public TransferDTOBuilder setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public TransferDTOBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public TransferDTOBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public TransferDTOBuilder setType(String transferType) {
            this.transferType = transferType;
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
