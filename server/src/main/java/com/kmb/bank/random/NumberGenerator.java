package com.kmb.bank.random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class NumberGenerator {

    @Autowired
    private SecureRandom secureRandom;

    public String returnRandomInts(int numberOfInts) {
        secureRandom.setSeed(System.currentTimeMillis());
        StringBuilder randomString = new StringBuilder();
        for(int i = 0; i < numberOfInts; i++) {
            randomString.append(secureRandom.nextInt(10));
        }
        return randomString.toString();
    }

}
