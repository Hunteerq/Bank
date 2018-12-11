package com.kmb.bank.services;

import com.kmb.bank.db.mongo.entity.TransferDTO;
import com.kmb.bank.db.mongo.repository.MongoTransactionRepository;
import com.kmb.bank.mapper.CurrenciesMapper;
import com.kmb.bank.models.CurrencyDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Service
public class DashboardService {

    @Autowired
    private CurrenciesMapper currenciesMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MongoTransactionRepository mongoTransactionRepository;

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

    public void addTransfersToModel(HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("username");

        Optional.ofNullable(jdbcTemplate.queryForMap("SELECT account.number, account.balance FROM account " +
                    "INNER JOIN client_account ON client_account.account_number = account.number " +
                    "INNER JOIN client ON client.pesel = client_account.client_pesel " +
                    "WHERE client.username = ? " +
                    "FETCH FIRST ROW ONLY", new Object[] {username}))
                .ifPresent(account -> addNumberBalanceAndTransfers(account, model));
    }

    private void addNumberBalanceAndTransfers(Map <String, Object> mainAccount, Model model) {
        model.addAttribute("accountNumber", mainAccount.get("number"));
        model.addAttribute("accountBalance", mainAccount.get("balance"));
        addTransfersToModelFromMongo((String)mainAccount.get("number"), model);

    }

    private void addTransfersToModelFromMongo(String mainAccountNumber, Model model) {
        List<TransferDTO> userTransfers = mongoTransactionRepository
                .findTransferDTOByUserAccountNumberOrderByLocalDateTime(mainAccountNumber, new PageRequest(0, 10));
        List<TransferDTO> recipientTransfers = mongoTransactionRepository
                .findTransferDTOByRecipientAccountNumberOrderByLocalDateTime(mainAccountNumber, new PageRequest(0, 10));

        List<TransferDTO> allList = Stream.of(userTransfers, recipientTransfers)
                .flatMap(List::stream)
                .sorted()
                .collect(Collectors.toList());

        addTransfersFromMongoToModel(allList, model);
    }

    private void addTransfersFromMongoToModel(List<TransferDTO> transfer, Model model) {
        model.addAttribute("transferDTOS", transfer);
    }
}
