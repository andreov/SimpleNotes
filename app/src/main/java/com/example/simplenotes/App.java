package com.example.simplenotes;


import android.app.Application;

public class App extends Application {
    private static KeyStore keyStore;
    private static NoteRepository dbNote;

    @Override
    public void onCreate() {
        super.onCreate();

        keyStore = new SimpleKeystore();
        dbNote=new NoteRepository((Application) getApplicationContext());

    }

    public static KeyStore getKeyStore() {
        return keyStore;
    }
    public static NoteRepository getDbNote() {
        return dbNote;
    }
}
