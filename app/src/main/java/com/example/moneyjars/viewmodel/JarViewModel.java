package com.example.moneyjars.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.moneyjars.entity.Jar;
import com.example.moneyjars.repository.JarRepository;

import java.util.List;

public class JarViewModel extends AndroidViewModel {

    private JarRepository repository;
    private LiveData<List<Jar>> listJar;

    public JarViewModel(@NonNull Application application) {
        super(application);
        repository = new JarRepository(application);
    }


    public LiveData<List<Jar>> getListJar(int id) {
        return repository.getListJar(id);
    }

    public void insertJar(Jar jar) {
        repository.insertJar(jar);
    }
}
