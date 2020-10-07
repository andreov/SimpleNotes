package com.example.simplenotes;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveSP {
    public static SharedPreferences sharedPreferences;

    //
    public static SharedPreferences init(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("Lockscreen", Context.MODE_PRIVATE);
            //put("SAVE","");
        }
        return sharedPreferences;
    }

    public static void put(String title, String value) {
        sharedPreferences.edit().putString(title, value).apply();
    }

    public static void put(String title, int value) {
        sharedPreferences.edit().putInt(title, value).apply();
    }

    public static String getString(String title, String defaultValue) {
        return sharedPreferences.getString(title, defaultValue);
    }

    public static int getInt(String title, int defaultValue) {
        return sharedPreferences.getInt(title, defaultValue);
    }

    public static void clearAll() {
        sharedPreferences.edit().clear().apply();
    }
}
