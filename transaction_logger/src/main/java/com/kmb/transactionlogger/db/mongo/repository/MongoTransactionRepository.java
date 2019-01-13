package com.kmb.transactionlogger.db.mongo.repository;

import com.kmb.transactionlogger.db.mongo.models.TransferToLogDTO;
import com.kmb.transactionlogger.models.TransferDTO;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MongoTransactionRepository extends MongoRepository<TransferToLogDTO, String> {

}
