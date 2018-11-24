package com.kmb.bank.services;

import com.kmb.bank.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class DashboardService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Collection<Transaction> getTransactions() {

        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1,
                "Artur Kulig",
                "1234124125",
                "12412412412",
                LocalDateTime.now(),
                521512.1,
                "For 2 ",
                Optional.empty()));

        transactions.add(new Transaction(2,
                "Filip Merta",
                "1234124125",
                "12412412412",
                LocalDateTime.now(),
                521512.1,
                "For 4",
                Optional.empty()));



        return transactions;
    }

}
