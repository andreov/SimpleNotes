package com.example.simplenotes;

import android.content.Context;

public interface KeyStore {
    void savePin(String pin);
    String readPin(Context context);
}
