package com.hopop.hopop.sidenavigation.suggestedroute.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.communicators.prefmanager.PrefManager;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.registration.activity.RegisterActivity;
import com.hopop.hopop.sidenavigation.profile.activity.Profile;
import com.hopop.hopop.sidenavigation.suggestedroute.data.ForSuggestedRoute;
import com.hopop.hopop.sidenavigation.suggestedroute.data.SuggestedInfo;
import com.hopop.hopop.source.activity.SourceActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestedRoute extends AppCompatActivity {
    private ProgressDialog progressDialog;
    @Bind(R.id.editText_Src)
    EditText pkupPoint;
    @Bind(R.id.editText_Drp)
    EditText drpPoint;
    String frmSplMob,lMob,rMob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_suggestedroute);
        ButterKnife.bind(this);
        setTitle("Suggest Routes");
        //Initialize a LoadViewTask object and call the execute() method
        new LoadViewTask().execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_keyboard_backspace_white_48px));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @OnClick(R.id.button_suggest)
    public void suggestRoute(View view)
    {
        if (checkFieldValidation()) {
            final ForSuggestedRoute forSuggestedRoute = new ForSuggestedRoute();
            lMob = LoginActivity.usrMobileNum;
            rMob = RegisterActivity.userMobNum;

            frmSplMob = PrefManager.getlMobile();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String systime = sdf.format(new Date());
            if(lMob ==null && rMob ==null)
            {
                forSuggestedRoute.setMobile_number(frmSplMob);
                forSuggestedRoute.setFrom_route(pkupPoint.getText().toString());
                forSuggestedRoute.setTo_route(drpPoint.getText().toString());
                forSuggestedRoute.getRequeste_on(systime);
                CommunicatorClass.getRegisterClass().forRoute(forSuggestedRoute).enqueue(new Callback<SuggestedInfo>() {
                    @Override
                    public void onResponse(Call<SuggestedInfo> call, Response<SuggestedInfo> response) {
                        Toast.makeText(SuggestedRoute.this, "The Request has been Accepted", Toast.LENGTH_LONG).show();
                        Intent suggIntent = new Intent(SuggestedRoute.this, SourceActivity.class);
                        suggIntent.putExtra("lMob",frmSplMob);
                        startActivity(suggIntent);
                    }
                    @Override
                    public void onFailure(Call<SuggestedInfo> call, Throwable t) {
                    }
                });
            }
            else if (lMob ==null)
            {
                forSuggestedRoute.setMobile_number(rMob);
                forSuggestedRoute.setFrom_route(pkupPoint.getText().toString());
                forSuggestedRoute.setTo_route(drpPoint.getText().toString());
                forSuggestedRoute.getRequeste_on(systime);
                CommunicatorClass.getRegisterClass().forRoute(forSuggestedRoute).enqueue(new Callback<SuggestedInfo>() {
                    @Override
                    public void onResponse(Call<SuggestedInfo> call, Response<SuggestedInfo> response) {
                        Toast.makeText(SuggestedRoute.this, "The Request has been Accepted", Toast.LENGTH_LONG).show();
                        Intent suggIntent = new Intent(SuggestedRoute.this, SourceActivity.class);
                        suggIntent.putExtra("lMob",frmSplMob);
                        startActivity(suggIntent);
                    }
                    @Override
                    public void onFailure(Call<SuggestedInfo> call, Throwable t) {
                    }
                });
            }
            else
            {
                forSuggestedRoute.setMobile_number(lMob);
                forSuggestedRoute.setFrom_route(pkupPoint.getText().toString());
                forSuggestedRoute.setTo_route(drpPoint.getText().toString());
                forSuggestedRoute.getRequeste_on(systime);
                CommunicatorClass.getRegisterClass().forRoute(forSuggestedRoute).enqueue(new Callback<SuggestedInfo>() {
                    @Override
                    public void onResponse(Call<SuggestedInfo> call, Response<SuggestedInfo> response) {
                        Toast.makeText(SuggestedRoute.this, "The Request has been Accepted", Toast.LENGTH_LONG).show();
                        Intent suggIntent = new Intent(SuggestedRoute.this, SourceActivity.class);
                        suggIntent.putExtra("lMob",frmSplMob);
                        startActivity(suggIntent);
                    }
                    @Override
                    public void onFailure(Call<SuggestedInfo> call, Throwable t) {
                    }
                });
            }

        }
    }
    boolean valid = true;

    private boolean checkFieldValidation() {
        if (pkupPoint.length() == 0) {
            pkupPoint.requestFocus();
            pkupPoint.setError("Source Point is required.");
            valid = false;
        } else if (drpPoint.length() == 0) {
            drpPoint.requestFocus();
            drpPoint.setError("Drop Point is required.");
            valid = false;
        }
        return valid=true;
    }

    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(SuggestedRoute.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                synchronized (this)
                {
                    int counter = 0;
                    while(counter <= 4)
                    {
                        this.wait(500);
                        counter++;
                        publishProgress(counter*25);
                    }
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result)
        {
            progressDialog.dismiss();
        }
    }
}