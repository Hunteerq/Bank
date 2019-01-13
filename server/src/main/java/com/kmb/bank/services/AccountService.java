package com.kmb.bank.services;

import com.kmb.bank.db.mongo.entity.TransferDTO;
import com.kmb.bank.db.postgres.repository.AccountRepository;
import com.kmb.bank.db.mongo.repository.MongoTransactionRepository;
import com.kmb.bank.mapper.TransferViewMapper;
import com.kmb.bank.models.TransferViewDTO;
import com.kmb.bank.models.account.AccountCurrencyDTO;
import com.kmb.bank.models.account.AccountSpecifiedViewDTO;
import com.kmb.bank.models.account.AccountTypeDTO;
import com.kmb.bank.models.currency.CurrencyChooseDTO;
import com.kmb.bank.random.NumberGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private NumberGenerator numberGenerator;

    @Autowired
    private MongoTransactionRepository mongoTransactionRepository;

    @Autowired
    private TransferViewMapper transferViewMapper;

    public boolean addAllAccountsToView(HttpServletRequest request, Model model) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if (username.isPresent()) {
            List<AccountCurrencyDTO> accountCurrencyDTOS = accountRepository.getAccountNumbersWithBalanceAndCurrency(username.get());
            model.addAttribute("accountCurrencyDTOS", accountCurrencyDTOS);
            return true;
        }
        return false;
    }

    public boolean createAccountView(HttpServletRequest request, Model model) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if (username.isPresent()) {
            List<CurrencyChooseDTO> currencyChooseDTOS = accountRepository.getCurrencies();
            List<AccountTypeDTO> accountTypeDTOS = accountRepository.getAccountTypes();
            model.addAttribute("currencyChooseDTOS", currencyChooseDTOS);
            model.addAttribute("accountTypeDTOS", accountTypeDTOS);
            return true;
        }
        return false;
    }

    public boolean createAccount(HttpServletRequest request, Model model, int accountTypeId, int currencyId) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if (username.isPresent()) {
            try {
                String newNumberAccountNumber = numberGenerator.returnRandomInts(16);
                String userPesel = accountRepository.getUserPesel(username.get());
                accountRepository.createAccount(newNumberAccountNumber, userPesel, accountTypeId, currencyId);
                return true;
            } catch (Exception e) {
                log.error("Error creating new account {}", e.getMessage());
            }
        }
        return false;
    }

    public boolean createSpecifiedAccountView(HttpServletRequest request, Model model, String accountNumber) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        return username.isPresent() && addAccountAndTransfersToModel(model, accountNumber);
    }

    private boolean addAccountAndTransfersToModel(Model model, String accountNumber) {
        Optional<AccountSpecifiedViewDTO> accountSpecifiedViewDTO =
                Optional.ofNullable(accountRepository.getSpecifiedAccountWithTypeBalanceAndCurrency(accountNumber));
        if (accountSpecifiedViewDTO.isPresent()) {
            model.addAttribute("accountSpecifiedViewDTO", accountSpecifiedViewDTO.get());
            addTransfersToModel(model, accountNumber);
            return true;
        }
        return false;
    }

    private void addTransfersToModel(Model model, String accountNumber) {

        List<TransferDTO> userTransfers = mongoTransactionRepository
                .findTransferDTOBySenderAccountNumberOrderByLocalDateTimeDesc(accountNumber, PageRequest.of(0, 10));
        List<TransferDTO> recipientTransfers = mongoTransactionRepository
                .findTransferDTOByRecipientAccountNumberOrderByLocalDateTimeDesc(accountNumber, PageRequest.of(0, 10));

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

    public boolean blockAccount(HttpServletRequest request, String accountNumber) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent()) {
            accountRepository.changeAccountStatus(accountNumber, false);
            return true;
        }
        return false;
    }

    public boolean unblockAccount(HttpServletRequest request, String accountNumber) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent()) {
            accountRepository.changeAccountStatus(accountNumber, true);
            return true;
        }
        return false;
    }

    public boolean createDeleteView(HttpServletRequest request, Model model, String accountNumber) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent()) {
            model.addAttribute("accountNumber", accountNumber);
            return true;
        }
        return false;
    }

    public boolean deleteAccount(HttpServletRequest request, String accountNumber) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent() && accountRepository.ifAccountNumberBelongsToUser(accountNumber, username.get())) {
            accountRepository.deleteSpecifiedAccount(accountNumber);
            return true;
        }
        return false;
    }
}