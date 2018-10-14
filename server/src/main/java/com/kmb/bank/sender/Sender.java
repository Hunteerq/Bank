package com.kmb.bank.sender;

public interface Sender {

    <T> void send  (T object) throws Exception;

}
