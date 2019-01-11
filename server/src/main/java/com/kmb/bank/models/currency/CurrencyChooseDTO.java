package com.kmb.bank.models.currency;

import lombok.Getter;

public class CurrencyChooseDTO {

    @Getter
    private final int id;
    @Getter
    private final String name;

    private CurrencyChooseDTO(CurrencyChooseDTOBuilder builder) {
        this.id = builder.getId();
        this.name = builder.getName();
    }

    public static CurrencyChooseDTOBuilder builder() {
        return new CurrencyChooseDTOBuilder();
    }

    public static final class CurrencyChooseDTOBuilder {

        @Getter
        private  int id;

        @Getter
        private  String name;

        public CurrencyChooseDTOBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public CurrencyChooseDTOBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CurrencyChooseDTO build() {
            return new CurrencyChooseDTO(this);
        }
    }
}
