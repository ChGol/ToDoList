package com.training.christian.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskCreateActivity extends AppCompatActivity {

    private TaskDataBase mTaskDatabase = new MemoryTaskDataBase();

    @BindView(R.id.task_title)
    EditText mTaskTitle;
    @BindView(R.id.task_note)
    EditText mTaskNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);
        ButterKnife.bind(this);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.btn_save)
    void onSaveClick() {
        ToDoTask task = new ToDoTask();
        task.setDateCreated(new Date());
        task.setName(mTaskTitle.getText().toString());
        task.setNote(mTaskNote.getText().toString());

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
