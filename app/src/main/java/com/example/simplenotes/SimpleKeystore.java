package com.example.simplenotes;

import android.content.Context;
import android.content.SharedPreferences;

public class SimpleKeystore implements KeyStore {
    public static SharedPreferences sharedPreferences;
    public static final String KEY_SP = "SaveSP";

    public String readPin(Context context) {
        String psw;
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("Lockscreen", Context.MODE_PRIVATE);
        }
        psw = sharedPreferences.getString(KEY_SP, "");
        return psw;
    }

    public void savePin(String pin) {
        sharedPreferences.edit().putString(KEY_SP, pin).apply();

    }

}
