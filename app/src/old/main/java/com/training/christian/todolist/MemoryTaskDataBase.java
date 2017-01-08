package com.training.christian.todolist;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MemoryTaskDataBase implements TaskDataBase {

    private static List<ToDoTask> mTasks = new LinkedList<>();

    @Override
    public List<ToDoTask> getTasks() {
        return Collections.unmodifiableList(mTasks);
    }

    @Override
    public void addTask(ToDoTask task) {
        mTasks.add(task);
    }

    @Override
    public ToDoTask getTask(int position) {
        return mTasks.get(position);
    }

    @Override
    public void updateTask(ToDoTask task, int position) {
        mTasks.set(position, task);
    }
}
