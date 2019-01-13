package com.kmb.transactionlogger.mapper;

import com.kmb.transactionlogger.db.mongo.models.TransferToLogDTO;
import com.kmb.transactionlogger.models.TransferDTO;
import org.springframework.stereotype.Component;

@Component
public class TransferToLogMapper {

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
}
