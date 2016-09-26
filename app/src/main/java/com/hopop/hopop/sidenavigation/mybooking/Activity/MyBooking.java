package com.hopop.hopop.sidenavigation.mybooking.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.communicators.prefmanager.PrefManager;
import com.hopop.hopop.database.BookingHistory;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.login.data.LoginUser;
import com.hopop.hopop.registration.activity.RegisterActivity;
import com.hopop.hopop.sidenavigation.mybooking.data.BookingHisInfo;
import com.hopop.hopop.source.activity.SourceActivity;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
    String frmSplMob,lMob,rMob;
    int pos_PrfPly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_mybooking);
        setTitle("Booking History");
        ButterKnife.bind(this);
        new LoadViewTask().execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_keyboard_backspace_white_48px));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                return;
            }
        });

        lMob = LoginActivity.usrMobileNum;
        rMob = RegisterActivity.userMobNum;
        frmSplMob = PrefManager.getlMobile();
        if(lMob ==null && rMob==null)
        {
            final  LoginUser loginUser = new LoginUser();
            loginUser.setMobile_number(frmSplMob);
            CommunicatorClass.getRegisterClass().bookingHis(loginUser).enqueue(new Callback<BookingHisInfo>() {
                @Override
                public void onResponse(Call<BookingHisInfo> call, Response<BookingHisInfo> response) {
                    BookingHisInfo bookingHisInfo = response.body();
                    for (BookingHistory bookingHistory : bookingHisInfo.getBookingHistory()) {
                        if(BookingHistory.isNew(bookingHistory.getMobileNumber()))
                        {
                            bookingHistory.save();
                        }
                        Log.d(getClass().getSimpleName(),"call booking history activity 1");
                        pastList = Select.from(BookingHistory.class).list();
                        Intent bhIntent = new Intent(MyBooking.this, BookingHistoryActivity.class);
                        bhIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(bhIntent);
                        finish();
                       /* if (pastList.size() == 0) {
                            btn_hist.setVisibility(View.INVISIBLE);
                        }
                        else {
                            btn_hist.setVisibility(View.VISIBLE);
                            ride.setVisibility(View.INVISIBLE);
                            bookNow.setVisibility(View.INVISIBLE);
                        }
*/
                    }
                }
                @Override
                public void onFailure(Call<BookingHisInfo> call, Throwable t) {
                }
            });
        }
        else if(lMob == null)
        {
            final  LoginUser loginUser = new LoginUser();
            loginUser.setMobile_number(rMob);
            CommunicatorClass.getRegisterClass().bookingHis(loginUser).enqueue(new Callback<BookingHisInfo>() {
                @Override
                public void onResponse(Call<BookingHisInfo> call, Response<BookingHisInfo> response) {
                    BookingHisInfo bookingHisInfo = response.body();
                    for (BookingHistory bookingHistory : bookingHisInfo.getBookingHistory()) {
                        if(BookingHistory.isNew(bookingHistory.getMobileNumber()))
                        {
                            bookingHistory.save();
                        }
                        Log.d(getClass().getSimpleName(),"call booking history activity 2");
                        pastList = Select.from(BookingHistory.class).list();
                        Intent bhIntent = new Intent(MyBooking.this, BookingHistoryActivity.class);
                        startActivity(bhIntent);
  /*                     if (pastList.size() == 0) {
                            btn_hist.setVisibility(View.INVISIBLE);
                        } else {

                            btn_hist.setVisibility(View.VISIBLE);
                            ride.setVisibility(View.INVISIBLE);
                            bookNow.setVisibility(View.INVISIBLE);

                        }*/
                    }
                }
                @Override
                public void onFailure(Call<BookingHisInfo> call, Throwable t) {
                }
            });
        }
        else
        {
            final  LoginUser loginUser = new LoginUser();
            loginUser.setMobile_number(lMob);
            CommunicatorClass.getRegisterClass().bookingHis(loginUser).enqueue(new Callback<BookingHisInfo>() {
                @Override
                public void onResponse(Call<BookingHisInfo> call, Response<BookingHisInfo> response) {
                    BookingHisInfo bookingHisInfo = response.body();
                    for (BookingHistory bookingHistory : bookingHisInfo.getBookingHistory()) {
                        if(BookingHistory.isNew(bookingHistory.getMobileNumber()))
                        {
                            bookingHistory.save();
                        }
                        Log.d(getClass().getSimpleName(),"call booking history activity 3");
                        pastList = Select.from(BookingHistory.class).list();
                        Intent bhIntent = new Intent(MyBooking.this, BookingHistoryActivity.class);
                        startActivity(bhIntent);
                      /* if (pastList.size() == 0) {
                            btn_hist.setVisibility(View.INVISIBLE);
                        } else {
                            btn_hist.setVisibility(View.VISIBLE);
                            ride.setVisibility(View.INVISIBLE);
                            bookNow.setVisibility(View.INVISIBLE);
                        }*/
                    }
                }
                @Override
                public void onFailure(Call<BookingHisInfo> call, Throwable t) {
                }
            });
        }
    }
    @OnClick(R.id.button_history)
    public void pastUser(View view) {
        Intent bhIntent = new Intent(MyBooking.this, BookingHistoryActivity.class);
        startActivity(bhIntent);
    }
    @OnClick(R.id.textView_booknow)
    public void bookNow(View view)
    {
        Intent bknw = new Intent(MyBooking.this, SourceActivity.class);
        bknw.putExtra("lMob",frmSplMob);
        bknw.putExtra("id",pos_PrfPly);
        startActivity(bknw);
    }

    public class LoadViewTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MyBooking.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            try {
                synchronized (this) {
                    int counter = 0;
                    while (counter <= 4) {
                        this.wait(500);
                        counter++;
                        publishProgress(counter * 25);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }

}
