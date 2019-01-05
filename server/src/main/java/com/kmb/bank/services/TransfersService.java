package com.kmb.bank.services;

import com.kmb.bank.models.AccountDTO;
import com.kmb.bank.models.TransferDTO;
import com.kmb.bank.sender.Sender;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Log4j2
@Service
public class TransfersService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Sender rabbitmq;

    private List<AccountDTO> accountDTOS = new ArrayList<>();

    public void registerAccountNumbers(HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("username");
        accountDTOS.clear();

        jdbcTemplate.query("SELECT account.number, account.balance FROM account "+
                        "INNER JOIN client_account ON client_account.account_number = account.number " +
                        "INNER JOIN client ON client.pesel = client_account.client_pesel " +
                        "WHERE client.username = ?", new Object[] {username},
                        (rs, rownum) -> AccountDTO.builder()
                                                    .setBalance(rs.getInt("balance"))
                                                    .setNumber(rs.getString("number"))
                                                    .build())
                        .forEach(this::addToCollection);

        register(model);
    }

    private void addToCollection(AccountDTO accountDTO) {
        accountDTOS.add(accountDTO);
    }

    private void register(Model model) {
        log.info("Registering account numbers for transfer view");
        model.addAttribute("accountDTOS", accountDTOS);
    }

    public void sendNormalTransfer(String userAccountNumber, String title, String recipientName, String recipientAccountNumber, String amount) {
        TransferDTO transferDTO = TransferDTO.builder()
                .setUserAccountNumber(userAccountNumber)
                .setTitle(title)
                .setRecipientName(recipientName)
                .setRecipientAccountNumber(recipientAccountNumber)
                .setAmount(Double.valueOf(amount))
                .setLocalDateTime(LocalDateTime.now())
                .build();

        try {
            rabbitmq.send(transferDTO);
            log.info("TransferDTO sent");
        } catch (Exception e) {
            log.debug("Error sending transferDTO " + e.getMessage());
        }

    }
}
