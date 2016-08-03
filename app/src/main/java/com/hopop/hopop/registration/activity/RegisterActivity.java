package com.hopop.hopop.registration.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.stetho.Stetho;
import com.hopop.hopop.login.activity.LoginActivity;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTouch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    public static final String PACKAGE = "com.hopop.hopop.login.activity";
	
	static final int DATE_DIALOG_ID = 0;
    private int mYear,mMonth,mDay;
    public static String userName = null;
    public static String userMobNum = null;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private Callback callback;
    TextView textView;
    final RegisterUser registerUser = new RegisterUser();
	
	@SuppressWarnings("deprecation")
    @SuppressLint("SimpleDateFormat")

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

                Intent intent = new Intent(RegisterActivity.this, SourceActivity.class);
                startActivity(intent);

            }

            @Override
            public void onCancel() {
                Intent cancelIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(cancelIntent);

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @OnClick(R.id.button_Lin)
    public void linkedInUser(View view) {
        login_linkedin();
    }

    public void login_linkedin() {
        LISessionManager.getInstance(getApplicationContext()).init(RegisterActivity.this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {

                Intent i = new Intent(getApplicationContext(), SourceActivity.class);
                startActivity(i);

                Toast.makeText(getApplicationContext(), "success" + LISessionManager.getInstance(getApplicationContext()).getSession().getAccessToken().toString(), Toast.LENGTH_LONG).show();

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
        Log.e("Register", "Jump from on Result");
        Intent intent = new Intent(RegisterActivity.this, SourceActivity.class);
        startActivity(intent);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // This method is used to make permissions to retrieve data from linkedin

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }

    // This Method is used to generate "Android Package Name" hash key

    public void generateHashkey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    PACKAGE,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, e.getMessage(), e);
        }

	
	Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dob.setText( sdf.format(c.getTime()));
        
        

        dob.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(DATE_DIALOG_ID);

            }
        });
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
    @Bind(R.id.editText_dob)
    EditText dob;
    @Bind(R.id.radioGroup)
    RadioGroup gender;
    @Bind(R.id.radioButton_male)
    RadioButton male;
    @Bind(R.id.radioButton_female)
    RadioButton female;
    @Bind(R.id.radioButton_other)
    RadioButton other;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    @OnClick(R.id.editText_dob)
    public void dobUser(View view)
    {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            //    String myFormat = "dd-MM-yyyy"; // your format
                String myFormat = "yyyy-MM-dd"; // your format
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                dob.setText(sdf.format(myCalendar.getTime()));
                Log.i(getClass().getSimpleName(),"dob:"+myCalendar.getTime());
            }

        };
        new DatePickerDialog(RegisterActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }


  String sex = null;

    @OnClick({ R.id.radioButton_male, R.id.radioButton_female,R.id.radioButton_other }) public void onRadioButtonClicked(RadioButton radioButton) {
        // Is the button now checked?
        boolean checked = radioButton.isChecked();

        // Check which radio button was clicked
        switch (radioButton.getId()) {
            case R.id.radioButton_male:
                if (checked) {

                     sex = male.getText().toString();
                }
                break;
            case R.id.radioButton_female:
                if (checked) {
                    sex = female.getText().toString();
                    Log.i(getClass().getSimpleName(),"female name:"+sex);
                }
                break;
            case R.id.radioButton_other:
                if(checked)
                {
                    sex = other.getText().toString();
                    Log.i(getClass().getSimpleName(),"other name:"+sex);
                }
                break;
            default:
                Log.i(getClass().getSimpleName(),"No one selected");
        }
    }
    @OnClick(R.id.button_Done)
    public void signUpUser(View view) {
        if (checkFieldValidation()) {
           // final RegisterUser registerUser = new RegisterUser();
            registerUser.setFirst_name(fName.getText().toString().trim());
            registerUser.setLast_name(lName.getText().toString().trim());
            registerUser.setMail_id(email.getText().toString().trim());
            registerUser.setMobile_number(mobile.getText().toString().trim());
            registerUser.setPassword(pass.getText().toString().trim());
            registerUser.setDob(dob.getText().toString().trim());
            registerUser.setGender(sex);

            userMobNum = registerUser.getMobile_number();

            Log.d("RANDOM TAG", "on submit button pressed");
            CommunicatorClass.getRegisterClass().groupListReg(registerUser).enqueue(new Callback<Registerresponse>() {
                @Override
                public void onResponse(Call<Registerresponse> call, Response<Registerresponse> response) {

                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                    Intent register = new Intent(RegisterActivity.this, SourceActivity.class);

                 /*  SharedPreferences preferences = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("uname", fName.getText().toString());
                    editor.putString("userMob",mobile.getText().toString());

                    editor.commit();

                    Bundle b = new Bundle();

                    b.putString("uname", fName.getText().toString());
                    b.putString("mobile",mobile.getText().toString());

                    register.putExtras(b);
*/
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
        String dobValidation = dob.getText().toString();

        if (fNameValidation.isEmpty() || fNameValidation.length() < 3)
        {
            fName.requestFocus();
            fName.setError("at least 3 characters");
            valid=false;
        }
        else if (!fNameValidation.matches("[a-zA-Z ]+"))
        {
            fName.requestFocus();
            fName.setError("Enter Only Alphabetical Character.");
            valid=false;
        }
        else if (lNameValidation.isEmpty() || lNameValidation.length() < 1)
        {
            lName.requestFocus();
            lName.setError("at least 1 characters");
            valid=false;
        }
        else if (!lNameValidation.matches("[a-zA-Z ]+"))
        {
            lName.requestFocus();
            lName.setError("Enter Only Alphabetical Character.");
            valid=false;
        }
        else if (email.length() == 0)
        {
            email.requestFocus();
            email.setError("Email is compulsory.");
            valid=false;
        }
        else if (!emailValidation.matches("^[A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
        {
            email.requestFocus();
            email.setError("Enter Valid Email Id.");
            valid=false;
        }
        else if (mobile.length() == 0)
        {
            mobile.requestFocus();
            mobile.setError("Mobile Number is compulsory.");
            valid=false;
        }
        else if (!mobileValidation.matches("^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$"))
        {
            mobile.requestFocus();
            mobile.setError("Enter Valid Mobile Number.");
            valid=false;
        }
        else if (passwordValidation.isEmpty() || passwordValidation.length() < 6 || passwordValidation.length() > 10)
        {
            pass.requestFocus();
            pass.setError("between 6 and 10 alphanumeric characters");
            valid=false;
        }
        else if (dob.length() == 0)
        {
            dob.requestFocus();
            dob.setError("Date of Birth is compulsory.");
            valid=false;
        }
        else if (gender.getCheckedRadioButtonId() == -1)
        {

            Toast.makeText(RegisterActivity.this,"Pls Select gender",Toast.LENGTH_SHORT).show();
            valid=false;
        }
        else
        {
            valid=true;
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