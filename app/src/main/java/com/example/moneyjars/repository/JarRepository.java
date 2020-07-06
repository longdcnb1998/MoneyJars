package com.example.moneyjars.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.example.moneyjars.dao.JarDao;
import com.example.moneyjars.db.AppDatabase;
import com.example.moneyjars.entity.Jar;

import java.util.List;

public class JarRepository {

    private JarDao jarDao;
    private LiveData<List<Jar>> listJar;

    public JarRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        jarDao = database.jarDao();
    }

    public LiveData<List<Jar>> getListJar(int id){
        return jarDao.getListJar(id);
    }

    public void insertJar(Jar jar){
        AppDatabase.databaseWriteExecutor.execute(() ->{
            jarDao.insert(jar);
        });
    }
}
