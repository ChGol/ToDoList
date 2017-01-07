package com.training.christian.todolist;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class TaskCreateActivity extends AppCompatActivity {

    private TaskDataBase mTaskDatabase;

    @BindView(R.id.task_title)
    EditText mTaskTitle;
    @BindView(R.id.task_note)
    EditText mTaskNote;
    @BindView(R.id.task_reminder)
    CheckBox mTaskReminder;
    @BindView(R.id.task_reminder_date)
    DatePicker mTaskRemiderDate;
    @BindView(R.id.task_reminder_time)
    TimePicker mTaskPicker;

    private int mPosition = -1;
    private ToDoTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);
        ButterKnife.bind(this);

        mTaskDatabase = new SQlLiteTaskDatabase(this);

        if (getIntent().hasExtra("pos")) {
            mPosition = getIntent().getIntExtra("pos", -1);
            mTask = mTaskDatabase.getTask(mPosition);
            mTaskTitle.setText(mTask.getName());
            mTaskNote.setText(mTask.getNote());
            if (mTask.isReminder()) {
                mTaskReminder.setChecked(true);
                Calendar reminderCalendar = Calendar.getInstance();
                reminderCalendar.setTime(mTask.getReminderDate());
                mTaskRemiderDate.init(reminderCalendar.get(Calendar.YEAR),
                        reminderCalendar.get(Calendar.MONTH),
                        reminderCalendar.get(Calendar.DAY_OF_MONTH), null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mTaskPicker.setHour(reminderCalendar.get(Calendar.HOUR_OF_DAY));
                    mTaskPicker.setMinute(reminderCalendar.get(Calendar.MINUTE));
                } else {
                    mTaskPicker.setCurrentHour(reminderCalendar.get(Calendar.HOUR_OF_DAY));
                    mTaskPicker.setCurrentMinute(reminderCalendar.get(Calendar.MINUTE));
                }
            }
        }

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    @OnCheckedChanged(R.id.task_reminder)
    void onReminderChecked(boolean checked) {
        mTaskRemiderDate.setVisibility(checked ? View.VISIBLE : View.GONE);
        mTaskPicker.setVisibility(checked ? View.VISIBLE : View.GONE);

    }

    @TargetApi(Build.VERSION_CODES.M)
    @OnClick(R.id.btn_save)
    void onSaveClick() {
        ToDoTask task = mTask != null ? mTask : new ToDoTask();
        task.setDateCreated(new Date());
        task.setName(mTaskTitle.getText().toString());
        task.setNote(mTaskNote.getText().toString());
        task.setReminder(mTaskReminder.isChecked());
        if (task.isReminder()) {
            // getHour weszła dopiero w API23 istnieje ze ryzyko ze bedziemy chcieli ja wywyolac w
            // sys ktory nie ma jej definicji
            // sprawdzamy wersje systemu i jezeli mozemy wywolujemy nowa wersje metody,
            // w przeciwnym rwzie stara getCurrentHour
            int hour = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ?
                    mTaskPicker.getHour() : mTaskPicker.getCurrentHour();

            int minute = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ?
                    mTaskPicker.getMinute() : mTaskPicker.getCurrentMinute();

            Calendar reminderCalendar = Calendar.getInstance();
            reminderCalendar.setTimeInMillis(0);
            reminderCalendar.set(mTaskRemiderDate.getYear(),
                    mTaskRemiderDate.getMonth(),
                    mTaskRemiderDate.getDayOfMonth(),
                    hour, minute);

            if (reminderCalendar.before(Calendar.getInstance())) {
                Toast.makeText(this, "Data powiadomienia musi być poźniejsz niż teraz !",
                        Toast.LENGTH_LONG)
                        .show();
                return;
            }

            task.setReminderDate(reminderCalendar.getTime());
        }

        if (mPosition == -1)
            mTaskDatabase.addTask(task);
        else
            mTaskDatabase.updateTask(task, mPosition);
        mTaskDatabase.addTask(task);

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
