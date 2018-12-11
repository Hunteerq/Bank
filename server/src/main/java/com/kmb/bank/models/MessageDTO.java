package com.kmb.bank.models;


import lombok.Getter;

import java.time.LocalDateTime;

public final class MessageDTO {
    private final String messageTitle;
    private final String message;
    private final LocalDateTime date;

    public static final MessageDTOBuilder builder() {
        return new MessageDTOBuilder();
    }

    private MessageDTO(MessageDTOBuilder messageDTOBuilder) {
        this.messageTitle = messageDTOBuilder.getMessageTitle();
        this.message = messageDTOBuilder.getMessage();
        this.date = messageDTOBuilder.getDate();
    }

    public static final class MessageDTOBuilder {
        @Getter
        private String messageTitle;
        @Getter
        private String message;
        @Getter
        private LocalDateTime date;

        public MessageDTOBuilder setMessageTitle(String messageTitle) {
            this.messageTitle = messageTitle;
            return this;
        }

        public MessageDTOBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public MessageDTOBuilder setDate(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public MessageDTO build() {
            return new MessageDTO(this);
        }
    }
}
