package com.bawp.todoister.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bawp.todoister.data.TaskDao;
import com.bawp.todoister.model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class},version = 1,exportSchema = false)
@TypeConverters({Convertor.class})
public abstract class TaskDatabase extends RoomDatabase {
    public static final int NUM_OF_THREADS = 4;
    public static final String DATABASE_NAME = "todoister_database";
    private static volatile TaskDatabase INSTANCE;
    public static final ExecutorService DataBaseWriterExecutor = Executors.newFixedThreadPool(NUM_OF_THREADS);

    public static final RoomDatabase.Callback TaskRoomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            DataBaseWriterExecutor.execute(() -> {
                TaskDao taskDao = INSTANCE.taskDao();

                taskDao.deleteAll();// Deletes all task;

            });
        }
    };

    public static TaskDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (TaskDatabase.class) {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),TaskDatabase.class,DATABASE_NAME)
                            .addCallback(TaskRoomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract TaskDao taskDao();



}
