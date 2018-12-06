package com.kmb.transactionlogger.currency;

import lombok.Getter;

public final class CurrencyDTO {

    @Getter
    private String name;
    @Getter
    private double rate;

    private CurrencyDTO (CurrencyDTOBuilder currencyDTOBuilder) {
        this.name = currencyDTOBuilder.getName();
        this.rate = currencyDTOBuilder.getRate();
    }

    public static CurrencyDTOBuilder builder() {
        return new CurrencyDTOBuilder();
    }

    public static final class CurrencyDTOBuilder {

        @Getter
        private String name;
        @Getter
        private double rate;

        public CurrencyDTOBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CurrencyDTOBuilder setRate(double rate) {
            this.rate = rate;
            return this;
        }

        public CurrencyDTO build() {
            return new CurrencyDTO(this);
        }
    }
}
