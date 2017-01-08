package com.akademiakodu.todolist.database;

import com.akademiakodu.todolist.model.TodoTask;

import java.util.List;

public interface ITaskDatabase {
    List<TodoTask> getTasks();

    TodoTask getTask(int position);

    void addTask(TodoTask task);

    void updateTask(TodoTask task, int position);
}
