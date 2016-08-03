package com.hopop.hopop.sidenavigation.feedback.activity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle("FeedBack");
        ButterKnife.bind(this);
        //Initialize a LoadViewTask object and call the execute() method
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

            String mob = LoginActivity.usrMobileNum;
            feedBackField.setRating(ratingBar.getRating());
            feedBackField.setComment(comments.getText().toString());
            feedBackField.setIs_on("f");
            feedBackField.setMobile_number(mob);

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
        //Before running code in separate thread
        @Override
        protected void onPreExecute()
        {
            //Create a new progress dialog
            progressDialog = new ProgressDialog(FeedBack.this);
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
