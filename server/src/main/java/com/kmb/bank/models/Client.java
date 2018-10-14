package com.kmb.bank.models;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Client {

    private String pesel;
    private String name;
    private String surname;
    private String email;
    private long addressId;
    private long loginId;
    private short color;
}
