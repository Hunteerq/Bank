package com.kmb.bank.db.mongo.repository;

import com.kmb.bank.db.mongo.entity.TransferDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface MongoTransactionRepository extends MongoRepository<TransferDTO, String> {

     List<TransferDTO> findTransferDTOBySenderAccountNumberOrderByLocalDateTimeDesc(String id, Pageable pageable);
     List<TransferDTO> findTransferDTOByRecipientAccountNumberOrderByLocalDateTimeDesc(String id, Pageable pagable);
}
