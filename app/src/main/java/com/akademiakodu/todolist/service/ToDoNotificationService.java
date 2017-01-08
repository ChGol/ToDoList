package com.akademiakodu.todolist.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.akademiakodu.todolist.R;
import com.akademiakodu.todolist.activity.TaskPreviewActivity;
import com.akademiakodu.todolist.database.ITaskDatabase;
import com.akademiakodu.todolist.database.SqliteTaskDatabase;
import com.akademiakodu.todolist.model.TodoTask;


public class ToDoNotificationService extends IntentService {

    public ToDoNotificationService() {
        super("ToDoNotificationService");
    }

    private ITaskDatabase mTaskDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mTaskDatabase = new SqliteTaskDatabase(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int taskId = intent.getIntExtra("id", -1);
        TodoTask task = mTaskDatabase.getTask(taskId);

        if (task == null) {
            //Jeżeli task nie istnieje nie robimy nic dalej
            return;
        }

        Intent previewIntent = new Intent(this, TaskPreviewActivity.class);
        previewIntent.putExtra("pos", taskId);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, taskId, previewIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(task.getName())
                .setContentInfo("Info")
                .setContentText("Przypominacz")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setTicker("Ticker Text")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(taskId, notification);
    }
}
