package com.hopop.hopop.sidenavigation.feedback.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.registration.activity.RegisterActivity;
import com.hopop.hopop.sidenavigation.feedback.data.FeedBackField;
import com.hopop.hopop.sidenavigation.feedback.data.FeedbackInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedBack extends AppCompatActivity {

    private ProgressDialog progressDialog;
    String frmSplMob,lMob,rMob;
    Context context;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle("FeedBack");
        ButterKnife.bind(this);
        new LoadViewTask().execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_keyboard_backspace_white_48px));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        information.setText("Please provide additional information or suggestions for improvement");
    }

    @Bind(R.id.textView_rate) TextView trip;
    @Bind(R.id.ratingBar) RatingBar ratingBar;
    @Bind(R.id.textView_information) TextView information;
    @Bind(R.id.edittText_comment) EditText comments;

    @OnClick(R.id.button_send)
    public void sendUser (View view){
        if(checkFieldValidation())
        {
            final FeedBackField feedBackField = new FeedBackField();
         //   String mob = LoginActivity.usrMobileNum;
            lMob = LoginActivity.usrMobileNum;
            rMob = RegisterActivity.userMobNum;
            context = FeedBack.this.getApplicationContext();
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            frmSplMob = sharedPreferences.getString("lMob",null);
            if(lMob ==null && rMob ==null)
            {
                feedBackField.setMobile_number(frmSplMob);
                feedBackField.setIs_on("f");
                feedBackField.setComment(comments.getText().toString().trim());
                feedBackField.setRating(ratingBar.getRating());
                CommunicatorClass.getRegisterClass().feedbackInfo(feedBackField).enqueue(new Callback<FeedbackInfo>() {
                    @Override
                    public void onResponse(Call<FeedbackInfo> call, Response<FeedbackInfo> response) {
                        Toast.makeText(FeedBack.this,"Your Feedback has been sent",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                    @Override
                    public void onFailure(Call<FeedbackInfo> call, Throwable t) {
                        Toast.makeText(FeedBack.this,"Failed to send your feedback",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else if(lMob ==null)
            {
                feedBackField.setMobile_number(rMob);
                feedBackField.setIs_on("f");
                feedBackField.setComment(comments.getText().toString().trim());
                feedBackField.setRating(ratingBar.getRating());
                CommunicatorClass.getRegisterClass().feedbackInfo(feedBackField).enqueue(new Callback<FeedbackInfo>() {
                    @Override
                    public void onResponse(Call<FeedbackInfo> call, Response<FeedbackInfo> response) {
                        Toast.makeText(FeedBack.this,"Your Feedback has been sent",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                    @Override
                    public void onFailure(Call<FeedbackInfo> call, Throwable t) {
                        Toast.makeText(FeedBack.this,"Failed to send your feedback",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {
                feedBackField.setMobile_number(lMob);
                feedBackField.setIs_on("f");
                feedBackField.setComment(comments.getText().toString().trim());
                feedBackField.setRating(ratingBar.getRating());
                CommunicatorClass.getRegisterClass().feedbackInfo(feedBackField).enqueue(new Callback<FeedbackInfo>() {
                    @Override
                    public void onResponse(Call<FeedbackInfo> call, Response<FeedbackInfo> response) {
                        Toast.makeText(FeedBack.this,"Your Feedback has been sent",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                    @Override
                    public void onFailure(Call<FeedbackInfo> call, Throwable t) {
                        Toast.makeText(FeedBack.this,"Failed to send your feedback",Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
    }

    @OnClick(R.id.button_cancel)
    public void cancelUser (View view){
        onBackPressed();
    }

    private boolean checkFieldValidation() {
        boolean valid = true;
        String cmmts = comments.getText().toString();
        float rting = ratingBar.getRating();
        if(rting == '0')
        {
            Toast.makeText(FeedBack.this,"Pls give the ratings.....",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else {
            valid = true;
        }
        return valid;
    }

    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(FeedBack.this);
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
