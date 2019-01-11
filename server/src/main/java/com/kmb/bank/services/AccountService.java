package com.kmb.bank.services;

import com.kmb.bank.db.mongo.repository.AccountRepository;
import com.kmb.bank.models.account.AccountCurrencyDTO;
import com.kmb.bank.models.account.AccountTypeDTO;
import com.kmb.bank.models.currency.CurrencyChooseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public boolean addAllAccountsToView(HttpServletRequest request, Model model) {
        Optional<String> username = Optional.ofNullable((String)request.getSession().getAttribute("username"));
        if(username.isPresent()) {
            List<AccountCurrencyDTO> accountCurrencyDTOS = accountRepository.getAccountNumbersWithBalanceAndCurrency(username.get());
            model.addAttribute("accountCurrencyDTOS", accountCurrencyDTOS);
            return true;
        }
        return false;
    }

    public boolean createAccount(HttpServletRequest request, Model model) {
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
}
