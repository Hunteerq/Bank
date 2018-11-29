package com.kmb.bank.db.mongo.repository;

import com.kmb.bank.db.mongo.entity.TransferDTO;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MongoTransactionRepository extends MongoRepository<TransferDTO, String> {

    public TransferDTO findFirstByUserAccountNumber(String id);
}
