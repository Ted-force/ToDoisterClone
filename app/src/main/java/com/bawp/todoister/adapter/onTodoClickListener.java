package com.bawp.todoister.adapter;

import com.bawp.todoister.model.Task;

public interface onTodoClickListener {
    void toDoClick(int adpaterPosition, Task task);
    void onTodoRadioButtonClick(Task task);
}
