package com.kmb.bank.services;

import com.kmb.bank.models.AccountDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Log4j2
@Service
public class TransfersService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<AccountDTO> accountDTOS = new ArrayList<>();

    public void registerAccountNumbers(HttpServletRequest request, Model model) {
        log.info("Entering  register");
        String username = (String) request.getSession().getAttribute("username");

        accountDTOS.clear();
        log.info("Status of accountDTOS " +  accountDTOS.toString());

        jdbcTemplate.query("SELECT account.number, account.balance FROM account "+
                        "INNER JOIN client_account ON client_account.account_number = account.number " +
                        "INNER JOIN client ON client.pesel = client_account.client_pesel " +
                        "WHERE client.username = ?", new Object[] {username},
                        ((rs, rownum) -> AccountDTO.builder()
                                                    .setBalance(rs.getInt("balance"))
                                                    .setNumber(rs.getString("number"))
                                                    .build()))
                                                    .forEach(this::add);
        register(model);
    }

    private void register(Model model) {
        log.info("Registering everything");
        model.addAttribute("accountDTOS", accountDTOS);
    }

    private void add(AccountDTO accountDTO) {
        log.info("Creating our DTO: " +accountDTO.toString());
        accountDTOS.add(accountDTO);
    }
}
