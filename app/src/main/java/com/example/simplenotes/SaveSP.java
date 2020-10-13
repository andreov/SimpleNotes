package com.example.simplenotes;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveSP implements KeyStore {
    public static SharedPreferences sharedPreferences;
    public static final String KEY_SP = "SaveSP";



    public SharedPreferences init(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("Lockscreen", Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public void savePin(String pin) {
       sharedPreferences.edit().putString(KEY_SP, pin).apply();

    }

    @Override
    public boolean hasPin() {
        return false;
    }

    @Override
    public boolean checkPin(String pin) {
        return false;
    }
}
