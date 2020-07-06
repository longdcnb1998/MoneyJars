package com.example.moneyjars.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.moneyjars.entity.Jar;

import java.util.List;

@Dao
public interface JarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Jar jar);

    @Update
    void update(Jar jar);

    @Query("SELECT * FROM tb_jar WHERE userId =:id")
    LiveData<List<Jar>> getListJar(int id);

    @Query("SELECT * FROM tb_jar WHERE id =:id")
    LiveData<Jar> getJar(int id);
}
