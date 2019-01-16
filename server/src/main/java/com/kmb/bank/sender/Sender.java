package com.kmb.bank.sender;

public interface Sender {

    <T> void sendTransfer  (T object);
    <T> void sendCredit  (T object);

}
