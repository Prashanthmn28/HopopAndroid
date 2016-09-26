package com.hopop.hopop.infrastructure.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.hopop.hopop.communicators.interceptor.ApiRequestInterceptor;
import com.hopop.hopop.communicators.prefmanager.PrefManager;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.payment.activity.PaymentActivity;
import com.hopop.hopop.source.activity.SourceActivity;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;


import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreen extends Activity {
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView hopop, pc;
    private Handler handler = new Handler();
    String authenticationToken, lMob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar
        setContentView(R.layout.splash);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        hopop = (TextView) findViewById(R.id.textView);
        pc = (TextView) findViewById(R.id.textView2);



        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            //pc.setText(progressStatus + "%");
                        }
                    });
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (progressStatus == 100) {
                    authenticationToken = PrefManager.getAuehKey();
                    if (authenticationToken.equals("NA")) {
                        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    } else {
                        ApiRequestInterceptor apiRequestInterceptor = new ApiRequestInterceptor(authenticationToken);
                        lMob = PrefManager.getlMobile();
                        Intent splIntnet = new Intent(SplashScreen.this, SourceActivity.class);
                        splIntnet.putExtra("lMob", lMob);
                        Bundle bundle = new Bundle();
                        splIntnet.putExtras(bundle);
                        startActivity(splIntnet);
                    }
                    finish();
                }
            }
        }).start();
    }

    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;
            String customKey;

            if (data != null) {
                customKey = data.optString("customkey", null);
                if (customKey != null)
                    Log.i("OneSignalExample", "customkey set with value: " + customKey);
            }

            if (actionType == OSNotificationAction.ActionType.ActionTaken)
                Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);

            // The following can be used to open an Activity of your choice.

             Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
             intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
             startActivity(intent);

            // Follow the instructions in the link below to prevent the launcher Activity from starting.
            // https://documentation.onesignal.com/docs/android-notification-customizations#changing-the-open-action-of-a-notification
        }
    }


}
//}
