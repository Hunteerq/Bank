package com.kmb.bank.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @AllArgsConstructor
public class Client {

    private long pesel;
    private String email;
    private long addresId;
    private long logId;
    private short color;

    @Getter @Setter
    private String name;
    @Getter @Setter
    private String surname;
}
