package com.example.simplenotes;


import android.app.Application;

public class App extends Application {
    private static KeyStore keyStore;
    private static NoteRepository noteRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        keyStore = new SimpleKeystore();
        noteRepository=new NoteRepository((Application) getApplicationContext());

    }

    public static KeyStore getKeyStore() {
        return keyStore;
    }
    public static NoteRepository getNoteRepository() {
        return noteRepository;
    }
}
