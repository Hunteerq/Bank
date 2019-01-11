package com.kmb.bank.models.account;

import lombok.Getter;

public class AccountTypeDTO {

    @Getter
    private final int id;
    @Getter
    private final String type;


    public static AccountTypeDTOBuilder builder(){
        return new AccountTypeDTOBuilder();
    }

    private AccountTypeDTO(AccountTypeDTOBuilder builder) {
        this.id = builder.getId();
        this.type = builder.getType();
    }

    public static final class AccountTypeDTOBuilder {
        @Getter
        private  int id;

        @Getter
        private  String type;

        public AccountTypeDTOBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public AccountTypeDTOBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public AccountTypeDTO build () {
            return new AccountTypeDTO(this);
        }
    }
}
