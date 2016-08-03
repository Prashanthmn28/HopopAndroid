package com.hopop.hopop.sidenavigation.mybooking.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.hopop.hopop.sidenavigation.mybooking.data.BookingHisInfo;
import com.hopop.hopop.sidenavigation.mybooking.adapter.PastRecyclerAdapter;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past);
        ButterKnife.bind(this);
        //Initialize a LoadViewTask object and call the execute() method
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


        String mob = LoginActivity.usrMobileNum;
        LoginUser loginUser = new LoginUser();
        loginUser.setMobile_number(mob);
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

    private void displayThePastList(final List<BookingHistory> pastList) {

        PastRecyclerAdapter pastRecycAda = new PastRecyclerAdapter(pastList, getApplicationContext());
        past_list.setAdapter(pastRecycAda);

        ((PastRecyclerAdapter) pastRecycAda).setOnItemClickListener(new PastRecyclerAdapter.ItemClickListenr() {

            @Override
            public void onItemClick(int position, View v) {

                String srcPt = pastList.get(position).getFromLocation();
                String desPt = pastList.get(position).getToLocation();
                /*Bundle data= new Bundle();
                data.putString("src", srcPt);
                data.putString("dest",desPt);
*/
                Intent reIntent = new Intent(BookingHistoryActivity.this, PlyActivity.class);

                reIntent.putExtra("src",srcPt);
                reIntent.putExtra("dest",desPt);

                startActivity(reIntent);


            }

        });
    }

    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
        //Before running code in separate thread
        @Override
        protected void onPreExecute()
        {
            //Create a new progress dialog
            progressDialog = new ProgressDialog(BookingHistoryActivity.this);
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
