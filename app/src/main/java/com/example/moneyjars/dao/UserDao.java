package com.example.moneyjars.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.moneyjars.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM tb_user")
    LiveData<List<User>> getListUser();

    @Query("SELECT * FROM tb_user WHERE username =:username")
    LiveData<User> getUser(String username);

}
