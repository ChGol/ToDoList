package com.training.christian.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToDoListActivity extends AppCompatActivity implements ToDoTaskAdapter.OnClickListener {
    @BindView(R.id.task_list)
    RecyclerView mToDoList;

    private TaskDataBase mTaskDatabase;
    private ToDoTaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        ButterKnife.bind(this);

        mTaskDatabase = new SQlLiteTaskDatabase(this);

        // 1. W jakim układzie maja wyświtlać się elementy listy
        // Układ pionowy - 1 element a wiersz
        mToDoList.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ToDoTaskAdapter(mTaskDatabase.getTask(), this);
        mToDoList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setTasks(mTaskDatabase.getTask());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todolist, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_create) {
            Intent createTaskIntent = new Intent(this, TaskCreateActivity.class);
            startActivity(createTaskIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(ToDoTask task, int position) {
        //zamiast Toast nowy Intent
        //Toast.makeText(this, "Klik "+position, Toast.LENGTH_SHORT).show();
        Intent createTaskIntent = new Intent(this, TaskCreateActivity.class);
        createTaskIntent.putExtra("pos", position);
        startActivity(createTaskIntent);
    }

    @Override
    public void onTaskDoneChanged(ToDoTask task, int position, boolean isDone) {
        //Toast.makeText(this, "Done "+position+" IsDone "+ isDone, Toast.LENGTH_LONG).show();
        task.setDone(isDone);
        mTaskDatabase.updateTask(task, position);
    }


}
