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

import com.hopop.hopop.communicators.interceptor.ApiRequestInterceptor;
import com.hopop.hopop.communicators.prefmanager.PrefManager;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.source.activity.SourceActivity;

public class SplashScreen extends Activity {
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView hopop, pc;
    private Handler handler = new Handler();
    Context context;
    String authenticationToken,lMob;
    SharedPreferences sharedPreferences;

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
                    context = SplashScreen.this.getApplicationContext();
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    authenticationToken = sharedPreferences.getString(PrefManager.AUTH_KEY, "NA");


                    if (authenticationToken.equals("NA")) {
                        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    } else {
                        String authKey = sharedPreferences.getString(PrefManager.getAuehKey(),null);
                        ApiRequestInterceptor apiRequestInterceptor = new ApiRequestInterceptor(authKey);
                        lMob = sharedPreferences.getString("lMob",null);
                        Log.i(getClass().getSimpleName(),"lMob in Splash:"+lMob);

                        Intent splIntnet = new Intent(SplashScreen.this, SourceActivity.class);
                        splIntnet.putExtra("lMob", lMob);
                        Bundle bundle = new Bundle();
                        splIntnet.putExtras(bundle);
                        startActivity(splIntnet);
                    }
                    finish();
                }


            }
        }).start();}

        public final boolean isInternetOn() {

            // get Connectivity Manager object to check connection
            ConnectivityManager connec =  (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

            // Check for network connections
            if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                    connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

                // if connected with internet

                Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
                return true;

            } else if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||  connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

                Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
                return false;
            }
            return false;
        }




}
