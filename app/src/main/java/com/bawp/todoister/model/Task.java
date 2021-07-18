package com.bawp.todoister.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task_table")
public class Task {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    public long id;

    @ColumnInfo(name = "Task")
    public String task;
    public Priority priority;

    @ColumnInfo(name = "due_date")
    public Date dueDate;

    @ColumnInfo(name = "date_created")
    public Date created;

    @ColumnInfo(name = "is_done")
    public boolean Completed;

    public Task(String task, Priority priority, Date dueDate, Date created,boolean Completed) {
        this.task = task;
        this.priority = priority;
        this.dueDate = dueDate;
        this.created = created;
        this.Completed = Completed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isCompleted() {
        return Completed;
    }

    public void setCompleted(boolean completed) {
        Completed = completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", created=" + created +
                ", Completed=" + Completed +
                '}';
    }
}
