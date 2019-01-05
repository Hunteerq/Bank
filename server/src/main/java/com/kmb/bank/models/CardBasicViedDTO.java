package com.kmb.bank.models;

public class CardBasicViedDTO {

    private final String cardNumber;
    private final String cardType;

    private CardBasicViedDTO(CardBasicViedDTOBuilder builder) {
        this.cardNumber = builder.getCardNumber();
        this.cardType = builder.getCardType();
    }

    public static CardBasicViedDTOBuilder builder() {
        return new CardBasicViedDTOBuilder();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public static class CardBasicViedDTOBuilder {

        private String cardNumber;
        private String cardType;

        public String getCardNumber() {
            return cardNumber;
        }

        public String getCardType() {
            return cardType;
        }

        public CardBasicViedDTOBuilder setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public CardBasicViedDTOBuilder setCardType(String cardType) {
            this.cardType = cardType;
            return this;
        }

        public CardBasicViedDTO build() {
            return new CardBasicViedDTO(this);
        }
    }

}
