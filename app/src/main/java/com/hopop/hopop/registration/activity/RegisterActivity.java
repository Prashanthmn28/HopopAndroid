package com.hopop.hopop.registration.activity;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.internal.Constants;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.stetho.Stetho;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.source.activity.SourceActivity;
import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.registration.data.RegisterUser;
import com.hopop.hopop.response.Registerresponse;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    public static final String PACKAGE = "com.hopop.hopop.login.activity";

    //private static final String TAG = "RegisterActivity";

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private Callback callback;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setTitle("Hop Up");
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Button button_Lin;


        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        loginButton.setReadPermissions("public_profile", "email", "user_friends");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e("ln: ", "Facebook Login Successful!");
                        Log.e("ln: ", "Logged in user Details : ");
                        Log.e("ln: ", "--------------------------");
                        Log.e("ln: ", loginResult.getAccessToken().getUserId());
                        Log.e("ln: ", loginResult.getAccessToken().getToken());


                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.i("LoginActivity", response.toString());
                                        try {
                                            String id = object.getString("id");
                                            try {
                                                URL profile_pic = new URL("http://graph.facebook.com/" + id + "/picture?type=small");
                                                Log.e("profile_pic", profile_pic + "");
                                            } catch (MalformedURLException e) {
                                                e.printStackTrace();
                                                }
                                            String name = object.getString("name");
                                            String email = object.getString("email");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday,picture.type(small)");
                        request.setParameters(parameters);
                        request.executeAsync();

                        Intent intent = new Intent(RegisterActivity.this,SourceActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
    }



    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }*/

    @OnClick(R.id.button_Lin)
    public void linkedInUser(View view){
        login_linkedin();
    }

    public void login_linkedin(){
        LISessionManager.getInstance(getApplicationContext()).init(RegisterActivity.this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {

                Intent i = new Intent(getApplicationContext(), SourceActivity.class);
                startActivity(i);
                //System.out.print("Logged in"+login_linkedin(););

                Toast.makeText(getApplicationContext(), "success" + LISessionManager.getInstance(getApplicationContext()).getSession().getAccessToken().toString(), Toast.LENGTH_LONG).show();
                //login_linkedin().setVisibility(View.GONE);

            }

            @Override
            public void onAuthError(LIAuthError error) {

                Toast.makeText(getApplicationContext(), "failed " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }, true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    // After complete authentication start new HomePage Activity

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("Register","Jump from on Result");
        //LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
        Intent intent = new Intent(RegisterActivity.this,SourceActivity.class);
        startActivity(intent);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    // This method is used to make permissions to retrieve data from linkedin

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }

    // This Method is used to generate "Android Package Name" hash key

    public void generateHashkey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    PACKAGE,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                /*((TextView) findViewById(R.id.package_name)).setText(info.packageName);
                ((TextView) findViewById(R.id.hash_key)).setText(Base64.encodeToString(md.digest(), Base64.NO_WRAP));*/
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, e.getMessage(), e);
        }
    }

    @Bind(R.id.editText_mn)
    EditText mobile;
    @Bind(R.id.editText_Psw)
    EditText pass;
    @Bind(R.id.editText_fn)
    EditText fName;
    @Bind(R.id.editText_ln)
    EditText lName;
    @Bind(R.id.editText_email)
    EditText email;

    @OnClick(R.id.button_Done)
    public void signUpUser(View view) {
        if (checkFieldValidation()) {
            RegisterUser registerUser = new RegisterUser();
            registerUser.setFirst_name(fName.getText().toString().trim());
            registerUser.setLast_name(lName.getText().toString().trim());
            registerUser.setMail_id(email.getText().toString().trim());
            registerUser.setMobile_number(mobile.getText().toString().trim());
            registerUser.setPassword(pass.getText().toString().trim());
            Log.d("RANDOM TAG", "on submit button pressed");
            CommunicatorClass.getRegisterClass().groupListReg(registerUser).enqueue(new Callback<Registerresponse>() {
                @Override
                public void onResponse(Call<Registerresponse> call, Response<Registerresponse> response) {

                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                    Intent register = new Intent(RegisterActivity.this, SourceActivity.class);
                    startActivity(register);
                    Log.e(getClass().getSimpleName(), "successful");

                }

                @Override
                public void onFailure(Call<Registerresponse> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Registration Unsuccessful", Toast.LENGTH_LONG).show();
                    Log.e(getClass().getSimpleName(), "failure");


                }


            });

        }
    }

    /*@OnClick(R.id.button_Lin)
    public void linkedInUser(View view) {
        //button_Lin();

    }*/



    /*@OnClick(R.id.button_fb)
    public void facebookUser(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/login/"));
        startActivity(browserIntent);
    }*/

    @OnTouch(R.id.Button_eye)
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pass.setInputType(InputType.TYPE_CLASS_TEXT);
                break;

            case MotionEvent.ACTION_UP:
                pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;

        }
        return true;
    }

    //checking field are empty
    private boolean checkFieldValidation() {

        boolean valid = true;
        String fNameValidation = fName.getText().toString();
        String lNameValidation = lName.getText().toString();
        String emailValidation = email.getText().toString();
        String mobileValidation = mobile.getText().toString();
        String passwordValidation = pass.getText().toString();

        if (fName.length() == 0) {
            fName.requestFocus();
            fName.setError("Field Cann't be Empty");
        } else if (!fNameValidation.matches("[a-zA-Z ]+")) {
            fName.requestFocus();
            fName.setError("Enter Only Alphabetical Character");
        } else if (lName.length() == 0) {
            lName.requestFocus();
            lName.setError("Field Cann't be Empty");
        } else if (!lNameValidation.matches("[a-zA-Z ]+")) {
            lName.requestFocus();
            lName.setError("Enter Only Alphabetical Character");
        } else if (email.length() == 0) {
            email.requestFocus();
            email.setError("Field Cann't be Empty");
        } else if (!emailValidation.matches("^[A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            email.requestFocus();
            email.setError("Enter Valid Email Id");
        } else if (mobile.length() == 0) {
            mobile.requestFocus();
            mobile.setError("Field Cann't be Empty");
        } else if (!mobileValidation.matches("^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$")) {
            mobile.requestFocus();
            mobile.setError("Enter Valid Mobile Number");
        } else if (pass.length() == 0) {
            pass.requestFocus();
            pass.setError("Field Cann't be Empty");
        } else {
            //no connection
            Toast.makeText(RegisterActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

        return valid;
    }




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen for landscape and portrait and set portrait mode always
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}