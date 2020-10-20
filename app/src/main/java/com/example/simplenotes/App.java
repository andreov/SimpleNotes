package com.example.simplenotes;

import android.app.Application;

public class App extends Application {
    private static KeyStore keyStore;

    @Override
    public void onCreate() {
        super.onCreate();

        keyStore = new SimpleKeystore();

    }

    public static KeyStore getKeyStore() {
        return keyStore;
    }
}
