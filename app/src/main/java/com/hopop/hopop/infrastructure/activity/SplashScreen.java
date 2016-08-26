package com.hopop.hopop.infrastructure.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.network.User;
import com.hopop.hopop.source.activity.SourceActivity;

public class SplashScreen extends Activity {
    SharedPreferences sharedPreferences;
    String authenticationToken;

    Context context;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView hopop, pc;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);	// Removes title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);	// Removes notification bar
        setContentView(R.layout.splash);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        hopop = (TextView) findViewById(R.id.textView);
        pc = (TextView) findViewById(R.id.textView2);
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100)
                {
                    progressStatus += 1;
                    handler.post(new Runnable()
                    {
                        public void run()
                        {
                            progressBar.setProgress(progressStatus);
                            //pc.setText(progressStatus + "%");
                        }
                    });
                    try
                    {
                        Thread.sleep(10);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                if (progressStatus==100)
                {
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }).start();


    /*context = SplashScreen.this.getApplicationContext();

    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    authenticationToken = sharedPreferences.getString(User.AUTHENTICATION_KEY, "NA");

    *//**
     * Authentication Token is checked,
     * if NA(Not Available) User will have to login
     * else User Redirected to Dashboard
     *//*
    if (authenticationToken.equals("NA")) {
        //if authentication key is not present
        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
    } else {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        //if authentication key is present open dashboard
        startActivity(new Intent(SplashScreen.this, SourceActivity.class));
    }

    finish();
*/
}
}
