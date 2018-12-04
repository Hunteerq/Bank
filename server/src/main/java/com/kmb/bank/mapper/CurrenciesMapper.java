package com.kmb.bank.mapper;

import com.kmb.bank.models.CurrencyDTO;

import java.util.Set;
import java.util.StringJoiner;

public class CurrenciesMapper {

    public String mapDtosToString(Set<CurrencyDTO> currencyDTOSet) {

        StringJoiner joiner = new StringJoiner("");

        currencyDTOSet.forEach(currency -> addCurrencyParameters(currency, joiner));
        return joiner.toString();
    }

    public void addCurrencyParameters(CurrencyDTO currencyDto, StringJoiner joiner) {
        joiner.add(currencyDto.toString());
    }


}
