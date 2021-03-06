package com.akademiakodu.todolist.database;

import android.content.Context;

import com.akademiakodu.todolist.model.TodoTask;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SqliteTaskDatabase implements ITaskDatabase {
    private Dao<TodoTask, Integer> mDao;

    public SqliteTaskDatabase(Context context) {
        TodoDbOpenHelper dbHelper = new TodoDbOpenHelper(context);
        ConnectionSource cs = new AndroidConnectionSource(dbHelper);
        try {
            mDao = DaoManager.createDao(cs, TodoTask.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TodoTask> getTasks() {
        try {
            return mDao.queryBuilder()
                    .orderBy("done", true)
                    .orderBy("dateCreated", false)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<TodoTask> getFutureTaskaWithReminder(Date now) {
        try {
            return mDao.queryBuilder()
                    .where().eq("reminder", true)
                    .and().ge("reminderDate", now)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    @Override
    public TodoTask getTask(int position) {
        try {
            return mDao.queryForId(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addTask(TodoTask task) {
        try {
            mDao.create(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTask(TodoTask task, int position) {
        try {
            mDao.update(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}













