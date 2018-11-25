package com.kmb.bank.services;

import com.kmb.bank.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class DashboardService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Collection<Transaction> getTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();


        return transactions;
    }

}
