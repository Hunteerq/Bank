package com.kmb.bank.mapper;

import com.kmb.bank.db.mongo.entity.TransferDTO;
import com.kmb.bank.models.TransferViewDTO;
import org.springframework.stereotype.Component;

@Component
public class TransferViewMapper {


    public TransferViewDTO incomingTransfer(TransferDTO transferDTO) {
        return TransferViewDTO.builder()
                .setName(transferDTO.getSenderName())
                .setAccountNumber(transferDTO.getSenderAccountNumber())
                .setTitle(transferDTO.getTitle())
                .setAmount(transferDTO.getAmount())
                .setType("Incoming")
                .setLocalDateTime(transferDTO.getLocalDateTime())
                .build();

    }

    public TransferViewDTO outgoingTransfer(TransferDTO transferDTO) {
        return TransferViewDTO.builder()
                .setName(transferDTO.getRecipientName())
                .setAccountNumber(transferDTO.getRecipientAccountNumber())
                .setTitle(transferDTO.getTitle())
                .setAmount(transferDTO.getAmount())
                .setType("Outgoing")
                .setLocalDateTime(transferDTO.getLocalDateTime())
                .build();
    }
}
