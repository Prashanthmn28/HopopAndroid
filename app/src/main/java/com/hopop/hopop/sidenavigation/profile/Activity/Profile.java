package com.hopop.hopop.sidenavigation.profile.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.database.ProfileDetail;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.login.data.LoginUser;
import com.hopop.hopop.sidenavigation.profile.data.ProfileDetailsInfo;
import com.hopop.hopop.sidenavigation.profile.data.ProfileUpdatedInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Bind(R.id.editText_fn) EditText firstName;
    @Bind(R.id.editText_ln) EditText lastName;
    @Bind(R.id.editText_email) EditText emailId;
    @Bind(R.id.editText_mn) EditText mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setTitle("User Details");
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

        String mob = LoginActivity.usrMobileNum;

        final LoginUser forUserProfile = new LoginUser();
        forUserProfile.setMobile_number(mob);


        CommunicatorClass.getRegisterClass().forProfile(forUserProfile).enqueue(new Callback<ProfileDetailsInfo>() {
            @Override
            public void onResponse(Call<ProfileDetailsInfo> call, Response<ProfileDetailsInfo> response) {
                Log.i(getClass().getSimpleName(),"UserProfile");

                ProfileDetailsInfo profileDetailsInfo = response.body();

                for (ProfileDetail profileDetail:profileDetailsInfo.getProfileDetails())
                {
                    String Fname = profileDetail.getFirstName().toString();

                    Log.i(getClass().getSimpleName(),"FirstName:"+Fname);

                    String Lname = profileDetail.getLastName().toString();
                    String Email = profileDetail.getMailId().toString();
                    String Mobile = profileDetail.getMobileNumber().toString();

                    firstName.setText(Fname);
                    lastName.setText(Lname);
                    emailId.setText(Email);
                    mobileNumber.setText(Mobile);

                }
            }

            @Override
            public void onFailure(Call<ProfileDetailsInfo> call, Throwable t) {

            }
        });
    }


    @OnClick(R.id.button_update)
    public void updateUser(View view){

        ProfileDetail updateProfile = new ProfileDetail();

        updateProfile.setFirstName(firstName.getText().toString());
        updateProfile.setLastName(lastName.getText().toString());
        updateProfile.setMailId(emailId.getText().toString());
        updateProfile.setMobileNumber(mobileNumber.getText().toString());

        CommunicatorClass.getRegisterClass().updateProfile(updateProfile).enqueue(new Callback<ProfileUpdatedInfo>() {
            @Override
            public void onResponse(Call<ProfileUpdatedInfo> call, Response<ProfileUpdatedInfo> response) {

                Toast.makeText(Profile.this,"Profile Update Successfully",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<ProfileUpdatedInfo> call, Throwable t) {

            }
        });


    }

    @OnClick(R.id.button_logout)
    public void logoutUser(View view){

      Intent lgIntent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(lgIntent);


    }

    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
        //Before running code in separate thread
        @Override
        protected void onPreExecute()
        {
            //Create a new progress dialog
            progressDialog = new ProgressDialog(Profile.this);
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