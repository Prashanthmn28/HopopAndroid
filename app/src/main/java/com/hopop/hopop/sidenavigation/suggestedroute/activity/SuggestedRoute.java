package com.hopop.hopop.sidenavigation.suggestedroute.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestedroute);
        ButterKnife.bind(this);
        setTitle("Suggest Routes");
        //Initialize a LoadViewTask object and call the execute() method
        new LoadViewTask().execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
            String userMobileNum = LoginActivity.usrMobileNum;
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String systime = sdf.format(new Date());

            forSuggestedRoute.setMobile_number(userMobileNum);
            forSuggestedRoute.setFrom_route(pkupPoint.getText().toString());
            forSuggestedRoute.setTo_route(drpPoint.getText().toString());
            forSuggestedRoute.getRequeste_on(systime);
            CommunicatorClass.getRegisterClass().forRoute(forSuggestedRoute).enqueue(new Callback<SuggestedInfo>() {

                @Override
                public void onResponse(Call<SuggestedInfo> call, Response<SuggestedInfo> response) {

                    Toast.makeText(SuggestedRoute.this, "The Request has been Accepted", Toast.LENGTH_LONG).show();

                    Intent suggIntent = new Intent(SuggestedRoute.this, SourceActivity.class);
                    startActivity(suggIntent);
                }

                @Override
                public void onFailure(Call<SuggestedInfo> call, Throwable t) {

                }
            });
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
        //Before running code in separate thread
        @Override
        protected void onPreExecute()
        {
            //Create a new progress dialog
            progressDialog = new ProgressDialog(SuggestedRoute.this);
            //Set the dialog title to 'Loading...'
            //progressDialog.setTitle("Loading...");
            //Set the dialog message to 'Loading application View, please wait...'
            progressDialog.setMessage("Loading...");
            //This dialog can't be canceled by pressing the back key
            progressDialog.setCancelable(false);
            //This dialog isn't indeterminate
            progressDialog.setIndeterminate(true);
            //The maximum number of items is 100
            progressDialog.setMax(100);
            //Set the current progress to zero
            progressDialog.setProgress(0);
            //Display the progress dialog
            progressDialog.show();
        }

        //The code to be executed in a background thread.
        @Override
        protected Void doInBackground(Void... params)
        {
            /* This is just a code that delays the thread execution 4 times,
             * during 850 milliseconds and updates the current progress. This
             * is where the code that is going to be executed on a background
             * thread must be placed.
             */
            try
            {
                //Get the current thread's token
                synchronized (this)
                {
                    //Initialize an integer (that will act as a counter) to zero
                    int counter = 0;
                    //While the counter is smaller than four
                    while(counter <= 4)
                    {
                        //Wait 850 milliseconds
                        this.wait(500);
                        //Increment the counter
                        counter++;
                        //Set the current progress.
                        //This value is going to be passed to the onProgressUpdate() method.
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

        //Update the progress
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            //set the current progress of the progress dialog
            progressDialog.setProgress(values[0]);
        }

        //after executing the code in the thread
        @Override
        protected void onPostExecute(Void result)
        {
            //close the progress dialog
            progressDialog.dismiss();
            //initialize the View
            //setContentView(R.layout.content_booking);
        }
    }
}