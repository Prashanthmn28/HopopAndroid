package com.hopop.hopop.sidenavigation.profile;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.login.data.LoginUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

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


    }
}
