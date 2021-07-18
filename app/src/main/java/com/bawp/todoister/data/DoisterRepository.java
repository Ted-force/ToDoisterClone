package com.bawp.todoister.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.bawp.todoister.model.Task;
import com.bawp.todoister.utils.TaskDatabase;

import java.util.List;

public class DoisterRepository {
    private final TaskDao taskDao;
    private final LiveData<List<Task>> allTasks;


    public DoisterRepository(Application application) {
        TaskDatabase Database = TaskDatabase.getDatabase(application);

        taskDao = Database.taskDao();
        allTasks = taskDao.getTasks();
    }

    public LiveData<List<Task>> getAllTasks() { return allTasks; }

    public void insert(Task task) {
        TaskDatabase.DataBaseWriterExecutor.execute(() -> {
            taskDao.insertTask(task);
        });
    }

    public LiveData<Task> GetTask(long id) {
        return taskDao.getOneTask(id);
    }

    public void update(Task task) {
        TaskDatabase.DataBaseWriterExecutor.execute(() -> {
            taskDao.Updatetask(task);
        });

    }

    public void DeleteTask(Task task) {
        TaskDatabase.DataBaseWriterExecutor.execute(() -> {
            taskDao.Delete(task);
        });
    }
}
