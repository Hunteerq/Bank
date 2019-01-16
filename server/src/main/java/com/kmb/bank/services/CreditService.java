package com.kmb.bank.services;

import com.kmb.bank.db.postgres.repository.AccountRepository;
import com.kmb.bank.db.postgres.repository.CreditRepository;
import com.kmb.bank.db.postgres.repository.DashboardRepository;
import com.kmb.bank.models.account.AccountCurrencyDTO;
import com.kmb.bank.models.credit.CreditBasicViewDTO;
import com.kmb.bank.models.credit.CreditEventDTO;
import com.kmb.bank.models.currency.CurrencyChooseDTO;
import com.kmb.bank.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class CreditService {

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private Sender rabbit;

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

    @Transactional
    public boolean addCredit(HttpServletRequest request, String accountNumber, short currencyId, double amount, short monthsAmount) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent() && accountRepository.ifAccountNumberBelongsToUser(accountNumber, username.get())) {
            Optional<String> currencyName = Optional.ofNullable(dashboardRepository.getCurrencyNameFromCurrencyId(currencyId));
            Optional<CreditEventDTO> creditEventDTO = currencyName
                    .map(currency -> buildCreditEventDto(accountNumber, amount, currency, monthsAmount, currencyId, username.get()));
            creditEventDTO.ifPresent(rabbit::sendCredit);
            return true;
        }
        return false;
    }

    private CreditEventDTO buildCreditEventDto(String accountNumber, double amount, String currencyName, short monthsAmount, short currencyId, String username) {
        return CreditEventDTO.builder()
                    .setAccountNumber(accountNumber)
                    .setUsername(username)
                    .setAmount(amount)
                    .setCurrency(currencyName)
                    .setCurrencyId(currencyId)
                    .setMonthsAmount(monthsAmount)
                    .build();
    }
}
