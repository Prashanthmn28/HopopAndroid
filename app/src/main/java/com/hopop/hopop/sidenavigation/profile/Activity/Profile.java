package com.hopop.hopop.sidenavigation.profile.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ParseException;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.database.ProfileDetail;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.login.data.LoginUser;
import com.hopop.hopop.registration.activity.RegisterActivity;
import com.hopop.hopop.sidenavigation.profile.data.ProfileDetailsInfo;
import com.hopop.hopop.sidenavigation.profile.data.ProfileUpdatedInfo;
import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;

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

    String frmSplMob,lMob,rMob;
    Context context;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setTitle("User Details");
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

        lMob = LoginActivity.usrMobileNum;
        rMob = RegisterActivity.userMobNum;
        context = Profile.this.getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        frmSplMob = sharedPreferences.getString("lMob",null);

        if(lMob ==null && rMob ==null)
        {
            final LoginUser forUserProfile = new LoginUser();
            forUserProfile.setMobile_number(frmSplMob);
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
        else if(lMob == null)
        {
            final LoginUser forUserProfile = new LoginUser();
            forUserProfile.setMobile_number(rMob);
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
        else
        {
            final LoginUser forUserProfile = new LoginUser();
            forUserProfile.setMobile_number(lMob);
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
    public void logoutUser(View view) {

        SharedPreferences mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.remove("mobileNumber");
        //editor.remove("Password");
        editor.commit();
        Message myMessage = new Message();
        myMessage.obj = "NOTSUCCESS";
        handler.sendMessage(myMessage);
        finish();

    }






private Handler handler = new Handler() {
@Override
public void handleMessage(Message msg) {
        String loginmsg=(String)msg.obj;
        if(loginmsg.equals("NOTSUCCESS")) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra("LoginMessage", "Logged Out");
        startActivity(intent);
        removeDialog(0);
        }
        }
        };

        /*SugarContext.terminate();
        SchemaGenerator schemaGenerator = new SchemaGenerator(getApplicationContext());
        schemaGenerator.deleteTables(new SugarDb(getApplicationContext()).getDB());
        SugarContext.init(getApplicationContext());
        schemaGenerator.createDatabase(new SugarDb(getApplicationContext()).getDB());
        Intent lgIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(lgIntent);*/


    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(Profile.this);
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