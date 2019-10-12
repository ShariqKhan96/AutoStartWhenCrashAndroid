package com.webxert.autostartwhencrashandroid;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by hp on 10/13/2019.
 */

public class BarService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Handler handler;
    int counter = 0;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // return super.onStartCommand(intent, flags, startId);
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent actIntent = PendingIntent.getActivity(this, 111, i, 0);
        Notification notification = new NotificationCompat.Builder(this, "123123")
                .setContentTitle("Check")
                .setContentText("Session is active")
                .setContentIntent(actIntent)
                .setColor(getResources().getColor(R.color.colorPrimaryDark))
                .build();
        startForeground(1, notification);
        startLog();
        return START_NOT_STICKY;
    }

    private void startLog() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("hey", counter++ + "");
                handler.postDelayed(this, 2000);
            }
        }, 500);
    }
}
