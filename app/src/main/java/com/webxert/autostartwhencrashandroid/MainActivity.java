package com.webxert.autostartwhencrashandroid;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    int counter = 0;
    TextView tv;



    @Override
    protected void onStart() {
        super.onStart();
        Log.e(MainActivity.class.getSimpleName(), "called :");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContextCompat.startForegroundService(this, new Intent(this, BarService.class));

        /*Server involvement while app crashes for any employee so when app launcehs again
        * mobile app will check is crash flag from server if its yes and person is clocked in
        * then start service again,autobroadcast receiver again, clokcout alarm again, servicecheck receiver again
        * */
//        String hello = getSharedPreferences("sh", MODE_PRIVATE).getString("crash", "null");
//        Log.e("auto_crash", hello + " MAIN ");
//        getSharedPreferences("sh", MODE_PRIVATE).edit().putString("crash", "false").apply();
//        Log.e("auto_crash", hello + " MAIN ");
        findViewById(R.id.c_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new RuntimeException();
            }
        });

        findViewById(R.id.c_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tv.setText("");
                } catch (Exception e) {
                    Log.e(MainActivity.class.getSimpleName(), e.getMessage());
                }
            }
        });
//
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                throw new RuntimeException();
//            }
//        }, 6000);
        InitilizerActivity initilizerActivity = new InitilizerActivity();
        initilizerActivity.setActivity(this);

    }
}
