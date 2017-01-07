package com.training.christian.todolist;

import android.content.Context;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class SQlLiteTaskDatabase implements TaskDataBase {

    private Dao<ToDoTask, Integer> mDao;

    public SQlLiteTaskDatabase(Context context) {
        ToDoDbOpenHelper dbHelper = new ToDoDbOpenHelper(context);
        ConnectionSource cs = new AndroidConnectionSource(dbHelper);
        try {
            mDao = DaoManager.createDao(cs, ToDoTask.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ToDoTask> getTask() {
        try {
            return mDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void addTask(ToDoTask task) {
        try {
            mDao.create(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ToDoTask getTask(int position) {
        try {
            return mDao.queryForId(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateTask(ToDoTask task, int position) {
        try {
            mDao.update(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
