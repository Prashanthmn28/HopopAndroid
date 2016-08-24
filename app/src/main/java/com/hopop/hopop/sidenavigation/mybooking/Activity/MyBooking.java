package com.hopop.hopop.sidenavigation.mybooking.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.database.BookingHistory;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.login.data.LoginUser;
import com.hopop.hopop.sidenavigation.mybooking.data.BookingHisInfo;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBooking extends AppCompatActivity {
    @Bind(R.id.imageView4)
    ImageView mapBus;
    @Bind(R.id.textView_ride)
    TextView ride;
    @Bind(R.id.textView_booknow)
    TextView bookNow;
    @Bind(R.id.button_history)
    Button btn_hist;
    private ProgressDialog progressDialog;
    List<BookingHistory> pastList = new ArrayList<BookingHistory>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mybooking);
        setTitle("Booking History");
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

        String mob = LoginActivity.usrMobileNum;

        Log.i(getClass().getSimpleName(),"login current mobile num:"+mob);

       final  LoginUser loginUser = new LoginUser();
        loginUser.setMobile_number(mob);
        CommunicatorClass.getRegisterClass().bookingHis(loginUser).enqueue(new Callback<BookingHisInfo>() {
            @Override
            public void onResponse(Call<BookingHisInfo> call, Response<BookingHisInfo> response) {

                BookingHisInfo bookingHisInfo = response.body();
                for (BookingHistory bookingHistory : bookingHisInfo.getBookingHistory()) {

                    if(BookingHistory.isNew(bookingHistory.getMobileNumber()))
                    {
                        bookingHistory.save();
                    }

                    pastList = Select.from(BookingHistory.class).list();
                    if (pastList.size() == 0) {
                        btn_hist.setVisibility(View.INVISIBLE);

                    } else {
                        btn_hist.setVisibility(View.VISIBLE);
                        ride.setVisibility(View.INVISIBLE);
                        bookNow.setVisibility(View.INVISIBLE);
                    }

                }

            }
            @Override
            public void onFailure(Call<BookingHisInfo> call, Throwable t) {

            }
        });

    }

    @OnClick(R.id.button_history)
    public void pastUser(View view) {

        Intent bhIntent = new Intent(MyBooking.this, BookingHistoryActivity.class);
        startActivity(bhIntent);
    }


    private class LoadViewTask extends AsyncTask<Void, Integer, Void> {
        //Before running code in separate thread
        @Override
        protected void onPreExecute() {
            //Create a new progress dialog
            progressDialog = new ProgressDialog(MyBooking.this);
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
        protected Void doInBackground(Void... params) {
            try {
                //Get the current thread's token
                synchronized (this) {
                    //Initialize an integer (that will act as a counter) to zero
                    int counter = 0;
                    //While the counter is smaller than four
                    while (counter <= 4) {
                        //Wait 850 milliseconds
                        this.wait(500);
                        //Increment the counter
                        counter++;
                        //Set the current progress.
                        //This value is going to be passed to the onProgressUpdate() method.
                        publishProgress(counter * 25);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        //Update the progress
        @Override
        protected void onProgressUpdate(Integer... values) {
            //set the current progress of the progress dialog
            progressDialog.setProgress(values[0]);
        }

        //after executing the code in the thread
        @Override
        protected void onPostExecute(Void result) {
            //close the progress dialog
            progressDialog.dismiss();
            //initialize the View
            //setContentView(R.layout.content_booking);
        }
    }

}