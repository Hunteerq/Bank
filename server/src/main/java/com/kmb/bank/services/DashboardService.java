package com.kmb.bank.services;

import com.kmb.bank.mapper.CurrenciesMapper;
import com.kmb.bank.models.CurrencyDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
public class DashboardService {

    @Autowired
    private CurrenciesMapper currenciesMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Set<CurrencyDTO> currencyDTOS = new HashSet<>();
    private Set<CurrencyDTO> cryptoCurrencyDTOS = new HashSet<>();

    public void getCurrencies(HttpServletRequest request) {
        currencyDTOS.clear();
        cryptoCurrencyDTOS.clear();

        Set<CurrencyDTO> currencyDTOS = jdbcTemplate.query("SELECT currency.name, currency_exchange.rate, currency.type FROM currency " +
                        "INNER JOIN currency_exchange on currency.id = currency_exchange.currency_id " +
                        "WHERE currency.name != 'PLN'",
                (rs, rownum) -> CurrencyDTO.builder()
                        .setName(rs.getString("name"))
                        .setType(rs.getString("type"))
                        .setRate(rs.getDouble("rate"))
                        .build())
                .stream().collect(Collectors.toSet());

        currencyDTOS.stream()
                .filter(currency -> currency.getType().equals("currency"))
                .forEach(this::addCurrency);

        currencyDTOS.stream()
                .filter(currency -> currency.getType().equals("crypto"))
                .forEach(this::addCryptoCurrency);

        addAllCurrenciesToSession(request);
    }

    private void addCurrency(CurrencyDTO currencyDTO) {
        currencyDTOS.add(currencyDTO);
    }

    private void addCryptoCurrency(CurrencyDTO cryptoCurrencyDTO) {
        cryptoCurrencyDTOS.add(cryptoCurrencyDTO);
    }

    private void addAllCurrenciesToSession(HttpServletRequest request) {
        request.getSession().setAttribute("currencies", currenciesMapper.mapDtosToString(currencyDTOS));
        request.getSession().setAttribute("crypto", currenciesMapper.mapDtosToString(cryptoCurrencyDTOS));
    }




}
