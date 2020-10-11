package com.example.simplenotes;

public interface KeyStore {
    void savePin2(String pin);
    boolean hasPin();
    boolean checkPin(String pin);

}
