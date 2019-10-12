package com.webxert.autostartwhencrashandroid;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

/**
 * Created by hp on 10/13/2019.
 */

public class CrashHandler extends Application {
    private Thread.UncaughtExceptionHandler defaultUEH;


    @Override
    public void onCreate() {
        super.onCreate();
        appInitialization();
    }

    private void appInitialization() {
        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(_unCaughtExceptionHandler);
    }

    private boolean isUIThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }


    // handler listener
    private Thread.UncaughtExceptionHandler _unCaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            // ex.printStackTrace();
            // TODO handle exception here

            if (isUIThread()) {
                // exception occurred from UI thread
                invokeSomeActivity();

            } else {  //handle non UI thread throw uncaught exception

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        invokeSomeActivity();
                    }
                });
            }
        }
    };

    private void invokeSomeActivity() {
        Log.e(CrashHandler.class.getSimpleName(), "invokeSomeActivity called :");
//        PendingIntent intent = PendingIntent.getActivity(
//                getBaseContext(),
//                0,
//                new Intent(new InitilizerActivity().getActivity().getIntent()),
//                new InitilizerActivity().getActivity().getIntent().getFlags());
//        AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 5000, intent);
//        System.exit(2);
        //new SharedPreferences().Editor.edit().


        getSharedPreferences("sh", MODE_PRIVATE).edit().putString("crash", "true").apply();
        String hello = getSharedPreferences("sh", MODE_PRIVATE).getString("crash", "null");
       // Log.e("auto_crash", hello);
        Intent mStartActivity = new Intent(getApplicationContext(), MainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 2000, mPendingIntent);
        android.os.Process.killProcess(Process.myPid());

    }
}
