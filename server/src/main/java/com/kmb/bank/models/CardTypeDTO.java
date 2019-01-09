package com.kmb.bank.models;

import lombok.Getter;

public class CardTypeDTO {

    @Getter
    private final int id;
    @Getter
    private final String type;

    private CardTypeDTO(CardTypeDTOBuilder cardTypeDTOBuilder) {
        this.id = cardTypeDTOBuilder.getId();
        this.type = cardTypeDTOBuilder.getType();
    }

    public static CardTypeDTOBuilder builder() {
        return new CardTypeDTOBuilder();
    }

    public static final class CardTypeDTOBuilder {

        @Getter
        private int id;
        @Getter
        private String type;

        public CardTypeDTOBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public CardTypeDTOBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public CardTypeDTO build() {
            return new CardTypeDTO(this);
        }
    }
}
