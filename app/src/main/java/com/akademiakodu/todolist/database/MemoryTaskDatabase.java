package com.akademiakodu.todolist.database;

import com.akademiakodu.todolist.model.TodoTask;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MemoryTaskDatabase implements ITaskDatabase {
    private static List<TodoTask> mTasks = new LinkedList<>();

    @Override
    public List<TodoTask> getTasks() {
        return Collections.unmodifiableList(mTasks);
    }

    @Override
    public TodoTask getTask(int position) {
        return mTasks.get(position);
    }

    @Override
    public void addTask(TodoTask task) {
        mTasks.add(task);
    }

    @Override
    public void updateTask(TodoTask task, int position) {
        mTasks.set(position, task);
    }
}
