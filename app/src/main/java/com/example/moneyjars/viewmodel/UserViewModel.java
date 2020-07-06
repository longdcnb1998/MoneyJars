package com.example.moneyjars.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.moneyjars.entity.User;
import com.example.moneyjars.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;

    private LiveData<List<User>> listUser;
    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        listUser = repository.getListUser();
    }

    public LiveData<List<User>> getListUser(){
        return listUser;
    }

    public LiveData<User> getUser(String username){
        return repository.getUser(username);
    }

    public void insertUser(User user){
        repository.insertUser(user);
    }
}
