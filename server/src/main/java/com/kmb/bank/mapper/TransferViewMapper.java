package com.kmb.bank.mapper;

import com.kmb.bank.db.mongo.entity.TransferToLogDTO;
import com.kmb.bank.models.transfer.TransferViewDTO;
import org.springframework.stereotype.Component;

@Component
public class TransferViewMapper {


    public TransferViewDTO incomingTransfer(TransferToLogDTO transferToLogDTO) {
        return TransferViewDTO.builder()
                .setName(transferToLogDTO.getSenderName())
                .setAccountNumber(transferToLogDTO.getSenderAccountNumber())
                .setTitle(transferToLogDTO.getTitle())
                .setAmount(transferToLogDTO.getAmountInRecipientCurrency())
                .setType("Incoming")
                .setLocalDateTime(transferToLogDTO.getLocalDateTime())
                .build();
    }

    public TransferViewDTO outgoingTransfer(TransferToLogDTO transferToLogDTO) {
        return TransferViewDTO.builder()
                .setName(transferToLogDTO.getRecipientName())
                .setAccountNumber(transferToLogDTO.getRecipientAccountNumber())
                .setTitle(transferToLogDTO.getTitle())
                .setAmount(transferToLogDTO.getAmount())
                .setType("Outgoing")
                .setLocalDateTime(transferToLogDTO.getLocalDateTime())
                .build();
    }
}
