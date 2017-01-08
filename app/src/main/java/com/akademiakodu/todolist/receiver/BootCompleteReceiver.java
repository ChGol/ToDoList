package com.akademiakodu.todolist.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.akademiakodu.todolist.NotificationPlaner;
import com.akademiakodu.todolist.database.ITaskDatabase;
import com.akademiakodu.todolist.database.SqliteTaskDatabase;

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ITaskDatabase taskDatabase = new SqliteTaskDatabase(context);

        new NotificationPlaner(taskDatabase, context).planNotifications();
    }
}
