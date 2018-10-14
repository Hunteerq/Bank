package com.kmb.bank.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
public class Address  {

    @Getter @Setter
    private long id;
    @Getter @Setter
    private String street;
    @Getter @Setter
    private int streetNumber;
    @Getter @Setter
    private int flatNumber;
    @Getter @Setter
    private String postalCode;
    @Getter @Setter
    private String Country;
}
