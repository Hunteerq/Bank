package com.kmb.transactionlogger.currency;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class CurrencyConverter {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static Map<String, Double> currenciesRates = new HashMap<>();

    @Scheduled(fixedRate = 600000)
    public void getCurrentRates()  {
        currenciesRates.clear();

        jdbcTemplate.query("SELECT currency.name, currency_exchange.rate FROM currency " +
                "INNER JOIN currency_exchange ON currency_exchange.currency_id = currency.id",
                (rs, rownum) -> CurrencyDTO.builder()
                                            .setName(rs.getString("name"))
                                            .setRate(rs.getDouble("rate"))
                                            .build())
                .forEach(this::addCurrency);

        log.info(currenciesRates);
    }

    private void addCurrency(CurrencyDTO currencyDTO) {
        currenciesRates.put(currencyDTO.getName(), currencyDTO.getRate());
    }

    public double convert(double amount, String currency) {
        return amount * currenciesRates.get(currency);
    }


}
