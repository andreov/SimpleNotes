package com.example.simplenotes;

public interface KeyStore {
    void savePin(String pin);
    boolean hasPin();
    boolean checkPin(String pin);

}
