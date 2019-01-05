package com.kmb.bank.mapper;

import com.kmb.bank.models.CurrencyDTO;

import java.util.Set;
import java.util.StringJoiner;

public class CurrenciesMapper {

    public String mapDtosToString(Set<CurrencyDTO> currencyDTOSet) {
        StringJoiner joiner = new StringJoiner("");
        currencyDTOSet.forEach(currency -> joiner.add(currency.toString()));

        return joiner.toString();
    }
}
