package com.kmb.bank.services;

import com.kmb.bank.db.mongo.entity.TransferToLogDTO;
import com.kmb.bank.db.postgres.repository.DashboardRepository;
import com.kmb.bank.db.mongo.repository.MongoTransactionRepository;
import com.kmb.bank.mapper.CurrenciesMapper;
import com.kmb.bank.mapper.TransferViewMapper;
import com.kmb.bank.models.transfer.TransferViewDTO;
import com.kmb.bank.models.account.AccountCurrencyDTO;
import com.kmb.bank.models.currency.CurrencyDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    private DashboardRepository dashboardRepository;

    @Autowired
    private MongoTransactionRepository mongoTransactionRepository;

    @Autowired
    private TransferViewMapper transferViewMapper;

    public boolean ifUserIsLogged(HttpServletRequest request) {
        return Optional.ofNullable((String) request.getSession().getAttribute("username")).isPresent();
    }

    public boolean prepareDashboardView(HttpServletRequest request, Model model) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent()){
            getCurrencies(request);
            addTransfersAndAccountToView(model, username.get());
            return true;
        }
        return false;
    }

    public void getCurrencies(HttpServletRequest request) {
        Set<CurrencyDTO> temporaryCurrencyDTOS = dashboardRepository.getCurrencies();
        Set<CurrencyDTO> currencyDTOS = temporaryCurrencyDTOS.stream()
                .filter(currency -> currency.getType().equals("currency"))
                .collect(Collectors.toSet());

        Set<CurrencyDTO> cryptoCurrencyDTOS = temporaryCurrencyDTOS.stream()
                .filter(currency -> currency.getType().equals("crypto"))
                .collect(Collectors.toSet());

        request.getSession().setAttribute("currencies", currenciesMapper.mapDtosToString(currencyDTOS));
        request.getSession().setAttribute("crypto", currenciesMapper.mapDtosToString(cryptoCurrencyDTOS));
    }


    private void addTransfersAndAccountToView(Model model, String username) {
        Optional<AccountCurrencyDTO> accountCurrencyDTO = Optional.ofNullable(dashboardRepository.getMainAccount(username));
        accountCurrencyDTO.ifPresent(account -> addNumberBalanceAndTransfers(account, model));
    }

    private void addNumberBalanceAndTransfers(AccountCurrencyDTO accountCurrencyDTO, Model model) {
        model.addAttribute("accountCurrencyDTO", accountCurrencyDTO);
        addTransfersToModelFromMongo(accountCurrencyDTO.getNumber(), model);

    }

    private void addTransfersToModelFromMongo(String mainAccountNumber, Model model) {
        List<TransferToLogDTO> userTransfers = mongoTransactionRepository
                .findTransferDTOBySenderAccountNumberOrderByLocalDateTimeDesc(mainAccountNumber, PageRequest.of(0, 10));
        List<TransferToLogDTO> recipientTransfers = mongoTransactionRepository
                .findTransferDTOByRecipientAccountNumberOrderByLocalDateTimeDesc(mainAccountNumber, PageRequest.of(0, 10));

        List<TransferViewDTO> transferViewDTOS = Stream.of(
                userTransfers.stream()
                        .map(transferViewMapper::outgoingTransfer),
                recipientTransfers.stream()
                        .map(transferViewMapper::incomingTransfer))
                .flatMap(stream -> stream)
                .sorted()
                .collect(Collectors.toList());

        model.addAttribute("transferViewDTOS", transferViewDTOS);
    }
}
