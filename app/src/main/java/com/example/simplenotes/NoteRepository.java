package com.example.simplenotes;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Абстрактный репозиторий, предложенный в Руководстве по архитектуре
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

class NoteRepository {

    private NoteDao mNoteDao;
    private LiveData<List<Note>> mAllNotes;

    NoteRepository(Application application) {
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(application);
        mNoteDao = db.noteDao();
        mAllNotes = mNoteDao.getAlphabetizedWords();
    }

    // Room выполняет все запросы в отдельном потоке.
    // Наблюдатель LiveData уведомит об изменении данных.
    LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    // Вы должны вызвать это в потоке, отличном от UI, иначе ваше приложение выдаст исключение.
    // Room гарантирует, что вы не будете выполнять какие-либо длительные операции в основном потоке,
    // блокируя пользовательский интерфейс
    void insert(Note note) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.insert(note);
        });
    }

    void delete(Note note){
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.delete(note);
        });
    }

    void update(Note note){
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.update(note);
        });
    }
}
