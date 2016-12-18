package com.training.christian.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToDoListActivity extends AppCompatActivity {
    @BindView(R.id.task_list)
    RecyclerView mToDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        ButterKnife.bind(this);

        // 1. W jakim układzie maja wyświtlać się elementy listy
        // Układ pionowy - 1 element a wiersz
        mToDoList.setLayoutManager(new LinearLayoutManager(this));

        List<ToDoTask> tasks = new LinkedList<>();
        ToDoTask task = new ToDoTask();
        task.setName("Zadanie1");
        task.setDone(true);
        tasks.add(task);

        ToDoTask task1 = new ToDoTask();
        task1.setName("Zadanie2");
        task1.setDone(true);
        tasks.add(task1);

        ToDoTaskAdapter adapter = new ToDoTaskAdapter(tasks);
        mToDoList.setAdapter(adapter);
    }
}
