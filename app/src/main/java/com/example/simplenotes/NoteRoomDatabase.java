package com.example.simplenotes;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


 // Это бэкэнд. База данных.

@Database(entities = {Note.class}, version = 7, exportSchema = false)
abstract class NoteRoomDatabase extends RoomDatabase {

    abstract NoteDao noteDao();

    // отметка экземпляра как изменчивого для обеспечения атомарного доступа к переменной
    private static volatile NoteRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static NoteRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteRoomDatabase.class, "note_database3")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // Если вы хотите сохранить данные через перезапуск приложения,
            // закомментируйте следующий блок
            //databaseWriteExecutor.execute(() -> {
                // Заполнение базы данных в фоновом режиме.
                // Если вы хотите начать с большего количества слов, просто добавьте их.
               // WordDao dao = INSTANCE.wordDao();
                //dao.deleteAll();
               //Word word = new Word("Hello", "World");
               //dao.insert(word);
//                word = new Word("World");
//                dao.insert(word);
            //});
        }
    };
}
