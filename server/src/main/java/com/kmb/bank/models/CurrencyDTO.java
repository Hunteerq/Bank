package com.kmb.bank.models;

import lombok.Getter;

public final  class CurrencyDTO {

    private CurrencyDTO(CurrencyDTOBuilder currencyDTOBuilder) {
        this.name = currencyDTOBuilder.getName();
        this.rate = currencyDTOBuilder.getRate();
        this.type = currencyDTOBuilder.getType();
    }

    @Getter
    private final String name;
    @Getter
    private final double rate;
    @Getter
    private final String type;

    public static CurrencyDTOBuilder builder() { return new CurrencyDTOBuilder(); }

    public static final class CurrencyDTOBuilder {

        @Getter
        private String name;
        @Getter
        private double rate;
        @Getter
        private String type;

        public CurrencyDTO build() {
            return new CurrencyDTO(this);
        }

       public CurrencyDTOBuilder setName(String name) {
           this.name = name;
           return this;
       }

       public CurrencyDTOBuilder setRate(double rate) {
           this.rate = rate;
           return this;
       }

       public CurrencyDTOBuilder setType(String type) {
            this.type = type;
            return this;
       }

    }

    @Override
    public String toString() {
        return getName() + ": " + getRate() + "      ";
    }
}
