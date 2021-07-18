package com.bawp.todoister;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bawp.todoister.model.Priority;
import com.bawp.todoister.model.SharedViewModel;
import com.bawp.todoister.model.Task;
import com.bawp.todoister.model.TaskViewModel;
import com.bawp.todoister.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private EditText enterTodo;
    private ImageButton calenderButton;
    private ImageButton priorityButton;
    private RadioGroup priorityRadioGroup;
    private RadioButton selectedRadioButton;
    private int selectedButtonId;
    private ImageButton saveButton;
    private CalendarView calendarView;
    private Group CalenderGroup;
    private Date dueDate;
    Calendar calendar = Calendar.getInstance();
    private SharedViewModel sharedViewModel;
    boolean isEdit;
    private Priority priority;

    public BottomSheetFragment() {}

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);

        CalenderGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar_view);
        calenderButton = view.findViewById(R.id.today_calendar_button);
        enterTodo = view.findViewById(R.id.enter_todo_et);
        saveButton = view.findViewById(R.id.save_todo_button);
        priorityButton = view.findViewById(R.id.priority_todo_button);
        priorityRadioGroup = view.findViewById(R.id.radioGroup_priority);

        Chip todayChip = view.findViewById(R.id.today_chip);
        todayChip.setOnClickListener(this);
        Chip tomorrowChip = view.findViewById(R.id.tomorrow_chip);
        tomorrowChip.setOnClickListener(this);
        Chip NextWeekChip = view.findViewById(R.id.next_week_chip);
        NextWeekChip.setOnClickListener(this);



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(sharedViewModel.getSelectedItem().getValue() != null) {
            Task task = sharedViewModel.getSelectedItem().getValue();
            isEdit = sharedViewModel.getIsEdit();
            enterTodo.setText(task.getTask());
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);



        calenderButton.setOnClickListener(v ->
                {
                    CalenderGroup.setVisibility(
                            CalenderGroup.getVisibility() == View.GONE ? View.VISIBLE:View.GONE
                    );
                    Utils.hideSoftKeyBoard(v);
                }


        );

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar.clear();
                calendar.set(year,month,dayOfMonth);
                dueDate = calendar.getTime();

            }
        });

        priorityButton.setOnClickListener(view12 -> {
            Utils.hideSoftKeyBoard(view12);
            priorityRadioGroup.setVisibility(
                    priorityRadioGroup.getVisibility() == View.GONE?View.VISIBLE:View.GONE
            );

            priorityRadioGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
                if(priorityRadioGroup.getVisibility() == View.VISIBLE) {
                    selectedButtonId = checkedId;
                    selectedRadioButton = view.findViewById(selectedButtonId);
                    if(selectedRadioButton.getId() == R.id.radioButton_high) {
                        priority = Priority.HIGH;
                    }else if(selectedRadioButton.getId() == R.id.radioButton_med) {
                        priority = Priority.MEDIUM;
                    }else if(selectedRadioButton.getId() == R.id.radioButton_low) {
                        priority = Priority.LOW;
                    }else {
                        priority = Priority.LOW;
                    }
                }else {
                    priority = Priority.LOW;
                }
            });
        });

        saveButton.setOnClickListener(v -> {
            String task = enterTodo.getText().toString().trim();



            if(!TextUtils.isEmpty(task) && dueDate != null && priority != null) {
                Task myTask = new Task(task, priority, dueDate,
                        Calendar.getInstance().getTime(),false);

                if(isEdit) {
                    Task Updatetask = sharedViewModel.getSelectedItem().getValue();
                    Updatetask.setTask(task);
                    Updatetask.setCreated(Calendar.getInstance().getTime());
                    Updatetask.setPriority(priority);
                    Updatetask.setDueDate(dueDate);
                    sharedViewModel.SetIsEdit(false);

                    if(dueDate.before(Calendar.getInstance().getTime())) {
//                        Snackbar.make(saveButton,R.string.invalid_date, Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(v.getContext(),R.string.invalid_date,Toast.LENGTH_LONG);
                    }else {
                        TaskViewModel.update(Updatetask);
                    }


                }else {
                    if(dueDate.before(Calendar.getInstance().getTime())) {
//                        Snackbar.make(saveButton,R.string.invalid_date, Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(v.getContext(),R.string.invalid_date,Toast.LENGTH_LONG);
                    }else {
                        TaskViewModel.insert(myTask);
                    }

                }
                enterTodo.setText("");
                if(this.isVisible()) {
                    this.dismiss();
                }


            }else {
                Snackbar.make(saveButton,R.string.empty_field,Snackbar.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.today_chip) {
            calendar.add(Calendar.DAY_OF_YEAR,0);
            dueDate = calendar.getTime();
        }else if(id == R.id.tomorrow_chip) {
            calendar.add(Calendar.DAY_OF_YEAR,1);
            dueDate = calendar.getTime();
        }else if(id == R.id.next_week_chip) {
            calendar.add(Calendar.DAY_OF_YEAR,7);
            dueDate = calendar.getTime();
        }

    }
}