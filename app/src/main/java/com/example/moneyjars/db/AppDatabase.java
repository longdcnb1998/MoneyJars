package com.example.moneyjars.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.moneyjars.dao.JarDao;
import com.example.moneyjars.dao.NoteHistoryDao;
import com.example.moneyjars.dao.UserDao;
import com.example.moneyjars.entity.Jar;
import com.example.moneyjars.entity.NoteHistory;
import com.example.moneyjars.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Jar.class, NoteHistory.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract JarDao jarDao();
    public abstract NoteHistoryDao historyDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (AppDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context,AppDatabase.class,"moneyOi.db")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    databaseWriteExecutor.execute(() -> {
                                        UserDao dao = INSTANCE.userDao();
                                        User user = new User("admin","admin@gmail.com","");
                                        dao.insert(user);
                                    });
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
