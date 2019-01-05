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

    private static Map<String, Map<String, Double>> currenciesRates = new HashMap<>();

    private static Map<String, Double> hashmapForPln = new HashMap<>();

    @Scheduled(fixedRate = 600000)
    public void getCurrentRates()  {
        log.info("Setting currencies");
        hashmapForPln.clear();
        currenciesRates.clear();

        jdbcTemplate.query("SELECT currency.name, currency_exchange.rate FROM currency " +
                "INNER JOIN currency_exchange ON currency_exchange.currency_id = currency.id",
                (rs, rownum) -> CurrencyDTO.builder()
                                            .setName(rs.getString("name"))
                                            .setRate(rs.getDouble("rate"))
                                            .build())
                .forEach(this::addCurrency);

        createHashmapForEveryCurrency();
    }

    private void addCurrency(CurrencyDTO currencyDTO) {
        hashmapForPln.put(currencyDTO.getName(),
                          currencyDTO.getRate());
    }

    private void createHashmapForEveryCurrency() {
        hashmapForPln.keySet()
                .forEach(this::addCurrencyMap);
    }

    private void addCurrencyMap(String currency) {
        Map<String, Double> mapOfCurrentCurrency = new HashMap<>();
        double rateOfThisCurrency = hashmapForPln.get(currency);

        hashmapForPln.keySet()
                .forEach(currencyInMap -> addSingleCurrencyToMap(currencyInMap, rateOfThisCurrency, mapOfCurrentCurrency));
        currenciesRates.put(currency, mapOfCurrentCurrency);
    }

    private void addSingleCurrencyToMap(String currencyInMap, double rateOfThisCurrency, Map<String, Double> mapOfCurrentCurrency) {
        mapOfCurrentCurrency.put(currencyInMap, rateOfThisCurrency / hashmapForPln.get(currencyInMap));
    }

    public double convertCurrencies(String currencyConverted, String currencyInResult, double amount) {
        return amount * currenciesRates.get(currencyConverted)
                                .get(currencyInResult);
    }

}
