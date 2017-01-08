package com.akademiakodu.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.akademiakodu.todolist.database.ITaskDatabase;
import com.akademiakodu.todolist.model.TodoTask;
import com.akademiakodu.todolist.service.ToDoNotificationService;

import java.util.Date;
import java.util.List;

public class NotificationPlaner {

    private ITaskDatabase mTaskDatabase;
    private Context mContext;

    public NotificationPlaner(ITaskDatabase mTaskDatabase, Context mContext) {
        this.mTaskDatabase = mTaskDatabase;
        this.mContext = mContext;
    }

    public void planNotifications() {
        //1. Pobrać powidomienia ktore mają włączone powiadiimienia
        // ale z czasem poniejszym niz teraz
        List<TodoTask> tasks = mTaskDatabase.getFutureTaskaWithReminder(new Date());

        //2. Dla tych zadan zaplanowac za pomoca AlarmManager'a
        //uslugi ToDoNotificationService
        AlarmManager alarmManager =
                (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        for (TodoTask task : tasks) {
            Intent serviceIntent = new Intent(mContext, ToDoNotificationService.class);
            serviceIntent.putExtra("id", task.getId());

            PendingIntent pendingIntent =
                    PendingIntent.getService(mContext, task.getId(),
                            serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            //set exact dopiero od api 19
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                        task.getReminderDate().getTime(), pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        task.getReminderDate().getTime(), pendingIntent);
            }
        }
    }
}
