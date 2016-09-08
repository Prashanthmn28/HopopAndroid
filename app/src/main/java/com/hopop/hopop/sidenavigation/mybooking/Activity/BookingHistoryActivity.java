package com.hopop.hopop.sidenavigation.mybooking.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.database.BookingHistory;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.login.data.LoginUser;
import com.hopop.hopop.ply.activity.PlyActivity;
import com.hopop.hopop.registration.activity.RegisterActivity;
import com.hopop.hopop.sidenavigation.mybooking.data.BookingHisInfo;
import com.hopop.hopop.sidenavigation.mybooking.adapter.PastRecyclerAdapter;
import com.hopop.hopop.source.activity.SourceActivity;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingHistoryActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    @Bind(R.id.past_list)
    RecyclerView past_list;
    List<BookingHistory> pastList = new ArrayList<BookingHistory>();
    List<BookingHistory> srcList = new ArrayList<BookingHistory>();
    List<BookingHistory> destList = new ArrayList<BookingHistory>();
    String frmSplMob,lMob,rMob;
    Context context;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past);
        ButterKnife.bind(this);
        new LoadViewTask().execute();
        setTitle("Booking History");
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
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        past_list.setLayoutManager(layoutManager);
        past_list.setItemAnimator(new DefaultItemAnimator());
      //  String mob = LoginActivity.usrMobileNum;
        lMob = LoginActivity.usrMobileNum;
        rMob = RegisterActivity.userMobNum;
        context = BookingHistoryActivity.this.getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        frmSplMob = sharedPreferences.getString("lMob",null);
        if(lMob ==null && rMob ==null)
        {
            LoginUser loginUser = new LoginUser();
            loginUser.setMobile_number(frmSplMob);
            CommunicatorClass.getRegisterClass().bookingHis(loginUser).enqueue(new Callback<BookingHisInfo>() {
                @Override
                public void onResponse(Call<BookingHisInfo> call, Response<BookingHisInfo> response) {
                    BookingHisInfo bookingHisInfo = response.body();
                    for (BookingHistory bookingHistory : bookingHisInfo.getBookingHistory()) {
                        if (BookingHistory.isNew(bookingHistory.getRoute())) {
                            bookingHistory.save();
                        }
                    }
                    pastList = Select.from(BookingHistory.class).list();
                    displayThePastList(pastList);
                    for (BookingHistory bookingHistory:bookingHisInfo.getBookingHistory()){
                        if(BookingHistory.isExisting(bookingHistory.getMobileNumber())){
                            bookingHistory.delete();
                        }
                    }
                }
                @Override
                public void onFailure(Call<BookingHisInfo> call, Throwable t) {
                }
            });
        }
        else if(lMob == null)
        {
            LoginUser loginUser = new LoginUser();
            loginUser.setMobile_number(rMob);
            CommunicatorClass.getRegisterClass().bookingHis(loginUser).enqueue(new Callback<BookingHisInfo>() {
                @Override
                public void onResponse(Call<BookingHisInfo> call, Response<BookingHisInfo> response) {
                    BookingHisInfo bookingHisInfo = response.body();
                    for (BookingHistory bookingHistory : bookingHisInfo.getBookingHistory()) {
                        if (BookingHistory.isNew(bookingHistory.getRoute())) {
                            bookingHistory.save();
                        }
                    }
                    pastList = Select.from(BookingHistory.class).list();
                    displayThePastList(pastList);
                    for (BookingHistory bookingHistory:bookingHisInfo.getBookingHistory()){
                        if(BookingHistory.isExisting(bookingHistory.getMobileNumber())){
                            bookingHistory.delete();
                        }
                    }
                }
                @Override
                public void onFailure(Call<BookingHisInfo> call, Throwable t) {
                }
            });
        }
        else
        {
            LoginUser loginUser = new LoginUser();
            loginUser.setMobile_number(lMob);
            CommunicatorClass.getRegisterClass().bookingHis(loginUser).enqueue(new Callback<BookingHisInfo>() {
                @Override
                public void onResponse(Call<BookingHisInfo> call, Response<BookingHisInfo> response) {
                    BookingHisInfo bookingHisInfo = response.body();
                    for (BookingHistory bookingHistory : bookingHisInfo.getBookingHistory()) {
                        if (BookingHistory.isNew(bookingHistory.getRoute())) {
                            bookingHistory.save();
                        }
                    }
                    pastList = Select.from(BookingHistory.class).list();
                    displayThePastList(pastList);
                    for (BookingHistory bookingHistory:bookingHisInfo.getBookingHistory()){
                        if(BookingHistory.isExisting(bookingHistory.getMobileNumber())){
                            bookingHistory.delete();
                        }
                    }
                }
                @Override
                public void onFailure(Call<BookingHisInfo> call, Throwable t) {
                }
            });
        }

    }

    private void displayThePastList(final List<BookingHistory> pastList) {
        PastRecyclerAdapter pastRecycAda = new PastRecyclerAdapter(pastList, getApplicationContext());
        past_list.setAdapter(pastRecycAda);
        ((PastRecyclerAdapter) pastRecycAda).setOnItemClickListener(new PastRecyclerAdapter.ItemClickListenr() {

            @Override
            public void onItemClick(int position, View v) {
                String srcPt = pastList.get(position).getFromLocation();
                String desPt = pastList.get(position).getToLocation();
                Intent reIntent = new Intent(BookingHistoryActivity.this, PlyActivity.class);
                reIntent.putExtra("src",srcPt);
                reIntent.putExtra("dest",desPt);
                reIntent.putExtra("lMob",frmSplMob);
                startActivity(reIntent);
            }

        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(BookingHistoryActivity.this);
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
