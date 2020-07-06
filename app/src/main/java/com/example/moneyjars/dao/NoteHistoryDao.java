package com.example.moneyjars.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.moneyjars.entity.NoteHistory;

import java.util.List;

@Dao
public interface NoteHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteHistory history);

    @Delete()
    void delete(NoteHistory history);

    @Query("SELECT * FROM tb_history WHERE userId =:userId")
    LiveData<List<NoteHistory>> getHistoryUser(int userId);

    @Query("SELECT * FROM tb_history WHERE jarId =:jarId")
    LiveData<List<NoteHistory>> getHistoryJar(int jarId);

//    @Query("SELECT * FROM tb_history WHERE timeStamp =:jarId")
//    LiveData<List<NoteHistory>> getHistoryJar(int jarId);
}
