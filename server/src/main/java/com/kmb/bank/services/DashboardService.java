package com.kmb.bank.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class DashboardService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @Autowired
//    private MongoTransactionRepository mongoTransactionRepository;

    public void getTransactions() {
      /*  log.info("xx");

        mongoTransactionRepository.save(new TransferDTO("12412", "aa", "ss", "32622632", 1321.51,LocalDateTime.now()));
        TransferDTO  transferDTO = mongoTransactionRepository.findFirstByUserAccountNumber("12412");
        log.info("Transfers: " + transferDTO.toString());
   */ }

}
