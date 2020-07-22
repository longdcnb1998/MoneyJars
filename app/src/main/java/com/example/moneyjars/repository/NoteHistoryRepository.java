package com.example.moneyjars.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.example.moneyjars.dao.NoteHistoryDao;
import com.example.moneyjars.db.AppDatabase;
import com.example.moneyjars.entity.NoteHistory;

import java.util.List;

public class NoteHistoryRepository {

    private NoteHistoryDao historyDao;
    private LiveData<List<NoteHistory>> listUserHistory;
    private LiveData<List<NoteHistory>> listJarHistory;

    public NoteHistoryRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        historyDao = database.historyDao();
    }

    public LiveData<List<NoteHistory>> getListUserHistory(int userId){
        return historyDao.getHistoryUser(userId);
    }

    public LiveData<List<NoteHistory>> getListJarHistory (int jarId){
        return historyDao.getHistoryJar(jarId);
    }

    public void insertHistory(NoteHistory noteHistory){
        AppDatabase.databaseWriteExecutor.execute(() ->{
            historyDao.insert(noteHistory);
        });
    }
}
