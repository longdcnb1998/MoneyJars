package com.example.moneyjars.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.example.moneyjars.dao.UserDao;
import com.example.moneyjars.db.AppDatabase;
import com.example.moneyjars.entity.User;

import java.util.List;

public class UserRepository {

    private UserDao userDao;
    private LiveData<List<User>> listUser;

    public UserRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        userDao = database.userDao();
        listUser = userDao.getListUser();
    }

    public LiveData<List<User>> getListUser(){
        return listUser;
    }
    public LiveData<User> getUser(String username){
        return userDao.getUser(username);
    }

    public void insertUser(User user){
        AppDatabase.databaseWriteExecutor.execute(() ->{
            userDao.insert(user);
        });
    }
}
