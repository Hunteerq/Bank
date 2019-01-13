package com.kmb.bank.services;

import com.kmb.bank.db.postgres.repository.AccountRepository;
import com.kmb.bank.db.postgres.repository.CreditRepository;
import com.kmb.bank.models.account.AccountCurrencyDTO;
import com.kmb.bank.models.credit.CreditBasicViewDTO;
import com.kmb.bank.models.currency.CurrencyChooseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class CreditService {

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private AccountRepository accountRepository;

    public boolean getAllCreditsView(HttpServletRequest request, Model model) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent()) {
            List<CreditBasicViewDTO>  creditBasicViewDTOS = creditRepository.getAllCredits(username.get());
            model.addAttribute("creditBasicViewDTOS", creditBasicViewDTOS);
            return true;
        }
        return false;
    }

    public boolean createAddCreditView(HttpServletRequest request, Model model) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent()) {
            List<AccountCurrencyDTO> accountCurrencyDTOS = accountRepository.getAccountNumbersWithBalanceAndCurrency(username.get());
            List<CurrencyChooseDTO> currencyChooseDTOS = accountRepository.getCurrencies();
            model.addAttribute("accountCurrencyDTOS", accountCurrencyDTOS);
            model.addAttribute("currencyChooseDTOS", currencyChooseDTOS);
            return true;
        }
        return false;
    }

    public boolean addCredit(HttpServletRequest request, Model model, String accountNumber, short currencyId, double amount, short monthsAmount) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent() && accountRepository.ifAccountNumberBelongsToUser(accountNumber, username.get())) {
            creditRepository.addCredit(accountNumber, currencyId, amount, monthsAmount);
            return true;
        }
        return false;
    }
}
