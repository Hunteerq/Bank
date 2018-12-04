package com.kmb.bank.db.mongo.repository;

import com.kmb.bank.db.mongo.entity.TransferDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface MongoTransactionRepository extends MongoRepository<TransferDTO, String> {

    public List<TransferDTO> findByUserAccountNumber(String id);
    public List<TransferDTO> findByRecipientAccountNumber(String id);
}
