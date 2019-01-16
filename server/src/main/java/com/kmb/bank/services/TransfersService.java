package com.kmb.bank.services;

import com.kmb.bank.db.postgres.repository.AccountRepository;
import com.kmb.bank.models.transfer.TransferDTO;
import com.kmb.bank.models.account.AccountCurrencyDTO;
import com.kmb.bank.sender.Sender;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Log4j2
@Service
public class TransfersService {


    @Autowired
    private Sender rabbitmq;

    @Autowired
    private AccountRepository accountRepository;

    public boolean registerAccountNumbers(HttpServletRequest request, Model model) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute("username"));
        if(username.isPresent()) {
            List<AccountCurrencyDTO> accountBasicViewDTOS = accountRepository.getAccountNumbersWithBalanceAndCurrency(username.get());
            model.addAttribute("accountDTOS", accountBasicViewDTOS);
            return true;
        }
        return false;
    }

    public boolean sendNormalTransfer(HttpServletRequest request, String senderAccountNumber, String title, String recipientName, String recipientAccountNumber, String amount) {
        try {
            Optional<String> senderName = Optional.ofNullable((String) request.getSession().getAttribute("nameSurname"));
            if (senderName.isPresent()) {
                TransferDTO transferDTO = TransferDTO.builder()
                        .setSenderAccountNumber(senderAccountNumber)
                        .setTitle(title)
                        .setRecipientName(recipientName)
                        .setSenderName(senderName.get())
                        .setRecipientAccountNumber(recipientAccountNumber)
                        .setAmount(Double.valueOf(amount))
                        .setLocalDateTime(LocalDateTime.now())
                        .build();
                rabbitmq.sendTransfer(transferDTO);
                return true;
            }
        } catch (Exception e) {
            log.error("Error sending transferDTO {}", e.getMessage());
        }
        return false;
    }
}
