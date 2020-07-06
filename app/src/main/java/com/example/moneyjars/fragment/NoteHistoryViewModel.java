package com.example.moneyjars.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.moneyjars.entity.Jar;
import com.example.moneyjars.entity.NoteHistory;
import com.example.moneyjars.repository.JarRepository;
import com.example.moneyjars.repository.NoteHistoryRepository;

import java.util.List;

public class NoteHistoryViewModel extends AndroidViewModel {

    private NoteHistoryRepository repository;
    private LiveData<List<Jar>> listJar;



    public NoteHistoryViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteHistoryRepository(application);
    }

    public LiveData<List<NoteHistory>> getHistoryJar(int id) {
        return repository.getListJarHistory(id);
    }


    public LiveData<List<NoteHistory>> getHistoryUser(int id) {
        return repository.getListUserHistory(id);
    }


    public void insertHistory(NoteHistory history) {
        repository.insertHistory(history);
    }
}
