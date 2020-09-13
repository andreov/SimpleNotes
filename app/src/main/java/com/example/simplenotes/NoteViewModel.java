package com.example.simplenotes;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

 // Просмотр модели для сохранения ссылки на репозиторий заметок и актуального списка всех заметок.

// Использование LiveData и кеширование того, что возвращает getAlphabetizedWords, имеет несколько преимуществ:
// Мы можем поставить наблюдателя на данные (вместо опроса на предмет изменений) и только обновить
// пользовательский интерфейс при фактическом изменении данных.
// Репозиторий полностью отделен от UI через ViewModel.

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository mRepository;
    private LiveData<List<Note>> mAllNotes;

    public NoteViewModel(Application application) {
        super(application);
        mRepository = new NoteRepository(application);
        mAllNotes = mRepository.getAllNotes();
    }

    LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    void insert(Note note) {
        mRepository.insert(note);
    }
    void delete(Note note) {
        mRepository.delete(note);
    }
    void update(Note note){
        mRepository.update(note);
    }
}
