package com.kmb.bank.services;

import com.kmb.bank.db.mongo.repository.AccountRepository;
import com.kmb.bank.models.account.AccountCurrencyDTO;
import com.kmb.bank.models.account.AccountTypeDTO;
import com.kmb.bank.models.currency.CurrencyChooseDTO;
import com.kmb.bank.random.NumberGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private NumberGenerator numberGenerator;

    public boolean addAllAccountsToView(HttpServletRequest request, Model model) {
        Optional<String> username = Optional.ofNullable((String)request.getSession().getAttribute("username"));
        if(username.isPresent()) {
            List<AccountCurrencyDTO> accountCurrencyDTOS = accountRepository.getAccountNumbersWithBalanceAndCurrency(username.get());
            model.addAttribute("accountCurrencyDTOS", accountCurrencyDTOS);
            return true;
        }
        return false;
    }

    public boolean createAccountView(HttpServletRequest request, Model model) {
        Optional<String> username = Optional.ofNullable((String)request.getSession().getAttribute("username"));
        if(username.isPresent()) {
            List<CurrencyChooseDTO> currencyChooseDTOS = accountRepository.getCurrencies();
            List<AccountTypeDTO> accountTypeDTOS = accountRepository.getAccountTypes();
            model.addAttribute("currencyChooseDTOS", currencyChooseDTOS);
            model.addAttribute("accountTypeDTOS", accountTypeDTOS);
            return true;
        }
        return false;
    }

    public boolean createAccount(HttpServletRequest request, Model model, int accountTypeId, int currencyId) {
        Optional<String> username = Optional.ofNullable((String)request.getSession().getAttribute("username"));
        if(username.isPresent()) {
            try {
                String newNumberAccountNumber = numberGenerator.returnRandomInts(16);
                String userPesel = accountRepository.getUserPesel(username.get());
                accountRepository.createAccount(newNumberAccountNumber, userPesel, accountTypeId, currencyId);
                return true;
            } catch(Exception e) {
                log.error("Error creating new account {}", e.getMessage());
            }
        }
        return false;
    }
}
