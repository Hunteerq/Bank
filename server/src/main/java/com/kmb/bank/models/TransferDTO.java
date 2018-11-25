package com.kmb.bank.models;

import lombok.Getter;

public final class TransferDTO {

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

    public static TransferDTOBuilder builder() {
        return new TransferDTOBuilder();
    }

    private TransferDTO(TransferDTOBuilder transferDTOBuilder) {
        this.userAccountNumber = transferDTOBuilder.getUserAccountNumber();
        this.title = transferDTOBuilder.getTitle();
        this.recipientName = transferDTOBuilder.getRecipientName();
        this.recipientAccountNumber = transferDTOBuilder.getRecipientAccountNumber();
        this.amount = transferDTOBuilder.getAmount();
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


        public TransferDTO build () {
            return new TransferDTO(this);
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

    }
}
