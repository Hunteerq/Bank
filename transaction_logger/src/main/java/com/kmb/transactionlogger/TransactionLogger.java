package com.kmb.transactionlogger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TransactionLogger {

	public static void main(String[] args) {
		SpringApplication.run(TransactionLogger.class, args);
	}
}
