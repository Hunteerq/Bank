package com.kmb.bank.db.mongo.repository;

import com.kmb.bank.db.mongo.entity.TransferToLogDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface MongoTransactionRepository extends MongoRepository<TransferToLogDTO, String> {

     List<TransferToLogDTO> findTransferDTOBySenderAccountNumberOrderByLocalDateTimeDesc(String id, Pageable pageable);
     List<TransferToLogDTO> findTransferDTOByRecipientAccountNumberOrderByLocalDateTimeDesc(String id, Pageable pagable);
}
