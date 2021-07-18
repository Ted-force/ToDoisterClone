package com.bawp.todoister.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.todoister.R;
import com.bawp.todoister.model.Task;
import com.bawp.todoister.utils.Utils;
import com.google.android.material.chip.Chip;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<Task> allTasks;
    private final onTodoClickListener todoClickListener;

    public RecyclerViewAdapter(List<Task> allTasks,onTodoClickListener ON_Todo_CLick_Listner) {
        this.allTasks = allTasks;
        this.todoClickListener = ON_Todo_CLick_Listner;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Task task = allTasks.get(position);
        String formatted = Utils.formatDate(task.getDueDate());

        ColorStateList colorStateList = new ColorStateList(new int[][]{
                new int[] {-android.R.attr.state_enabled},
                new int[] {android.R.attr.state_enabled}
        }, new int[]{
                Color.LTGRAY,
                Utils.priorityColor(task)
        });

        holder.task.setText(task.getTask());
        holder.todayChip.setText(formatted);
        holder.todayChip.setTextColor(Utils.priorityColor(task));
        holder.todayChip.setCheckedIconTint(colorStateList);
        holder.radioButton.setButtonTintList(colorStateList);
    }



    @Override
    public int getItemCount() {
        return allTasks.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        public AppCompatRadioButton radioButton;
        public AppCompatTextView task;
        public Chip todayChip;

        onTodoClickListener onTodoClickListener;


        public ViewHolder(@NonNull View itemView)  {
            super(itemView);

            radioButton = itemView.findViewById(R.id.todo_radio_button);
            task = itemView.findViewById(R.id.todo_row_todo);
            todayChip = itemView.findViewById(R.id.todo_row_chip);
            this.onTodoClickListener = todoClickListener;
            radioButton.setOnClickListener(this);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Task currTask;
            int id = v.getId();
            if(id == R.id.todo_row_layout) {
                 currTask = allTasks.get(getAdapterPosition());
                onTodoClickListener.toDoClick(getAdapterPosition(),currTask);
            }else if(id == R.id.todo_radio_button) {
                currTask = allTasks.get(getAdapterPosition());
                onTodoClickListener.onTodoRadioButtonClick(currTask);
            }

        }
    }
}
