package com.hopop.hopop.login.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.stetho.Stetho;
import com.hopop.hopop.registration.activity.RegisterActivity;
import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.login.data.LoginUser;
import com.hopop.hopop.response.Registerresponse;
import com.hopop.hopop.source.activity.SourceActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    public static String usrMobileNum = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setTitle("Hop In");
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Bind(R.id.editText_mn) EditText mobile;
    @Bind(R.id.editText_Psw) EditText pass;

    @OnClick (R.id.button_Login)
    public void loginUser(View view){
        if(checkFieldValidation()){

            final LoginUser loginUser = new LoginUser();
            loginUser.setMobile_number(mobile.getText().toString().trim());
            loginUser.setPassword(pass.getText().toString().trim());
            Log.d("RANDOM TAG", "on submit button pressed");
            CommunicatorClass.getRegisterClass().groupListLogin(loginUser).enqueue(new Callback<Registerresponse>() {
                @Override
                public void onResponse(Call<Registerresponse> call, Response<Registerresponse> response) {
                    Toast.makeText(LoginActivity.this, "Login SuccessFully", Toast.LENGTH_SHORT).show();
                    Intent searchIntent = new Intent(LoginActivity.this, SourceActivity.class);
                    startActivity(searchIntent);
                    usrMobileNum = loginUser.getMobile_number();
                    Log.e(getClass().getSimpleName(), "successful");

                }

                @Override
                public void onFailure(Call<Registerresponse> call, Throwable t) {
                    Log.e(getClass().getSimpleName(), "failure");
                    Toast.makeText(LoginActivity.this, "Invalid Mobile Number/Password", Toast.LENGTH_SHORT).show();
                    Log.e(getClass().getSimpleName(), "failure");

                }


            });
        }


    }

    @OnClick (R.id.button_signup)
    public void signupUser(View view){
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    //checking field are empty
    private boolean checkFieldValidation(){

        boolean valid=true;
        String mobileValidation = mobile.getText().toString();
        String passwordValidation = pass.getText().toString();

        if (mobile.length() == 0) {
            mobile.requestFocus();
            mobile.setError("Field Cann't be Empty");
        } else if (!mobileValidation.matches("^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$")) {
            mobile.requestFocus();
            mobile.setError("Enter Valid Mobile Number");
        } else if (passwordValidation.length() == 0) {
            pass.requestFocus();
            pass.setError("Field Cann't be Empty");
        }

        return valid;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen for landscape and portrait and set portrait mode always
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
