package com.kmb.transactionlogger.mapper;

import com.kmb.transactionlogger.db.mongo.models.TransferToLogDTO;
import com.kmb.transactionlogger.models.CreditEventDTO;
import com.kmb.transactionlogger.models.TransferDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RabbitObjectsMapper {

    public TransferToLogDTO transferToTransferLog(TransferDTO transferDTO, double amountInRecipientCurrency) {
        return TransferToLogDTO.builder()
                .senderName(transferDTO.getSenderName())
                .senderAccountNumber(transferDTO.getSenderAccountNumber())
                .recipientName(transferDTO.getRecipientName())
                .recipientAccountNumber(transferDTO.getRecipientAccountNumber())
                .amount(transferDTO.getAmount())
                .amountInRecipientCurrency(amountInRecipientCurrency)
                .title(transferDTO.getTitle())
                .localDateTime(transferDTO.getLocalDateTime())
                .build();
    }

    public TransferToLogDTO creditToTransferLog(CreditEventDTO creditEventDTO, double amountInRecipientCurrency) {
        return TransferToLogDTO.builder()
                .senderName("Bank")
                .senderAccountNumber("BANK_NUMBER")
                .recipientAccountNumber(creditEventDTO.getAccountNumber())
                .recipientName("Unimportant")
                .amount(creditEventDTO.getAmount())
                .amountInRecipientCurrency(amountInRecipientCurrency)
                .localDateTime(LocalDateTime.now())
                .title("Credit")
                .build();
    }
}
