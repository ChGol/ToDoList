package com.training.christian.todolist;

import java.util.List;

public interface TaskDataBase {

    List<ToDoTask> getTask();

    void addTask(ToDoTask task);

    ToDoTask getTask(int position);

    void updateTask(ToDoTask task, int position);

}