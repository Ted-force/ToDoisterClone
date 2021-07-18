package com.bawp.todoister.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bawp.todoister.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
     void insertTask(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getTasks();

    @Query("SELECT * FROM task_table WHERE task_table.ID == :id ")
    LiveData<Task> getOneTask(long id);

    @Update
    void Updatetask(Task task);

    @Delete
    void Delete(Task task);
}
