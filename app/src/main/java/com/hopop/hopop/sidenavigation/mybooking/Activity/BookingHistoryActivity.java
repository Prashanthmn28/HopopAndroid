package com.hopop.hopop.sidenavigation.mybooking.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.database.BookingHistory;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.login.data.LoginUser;
import com.hopop.hopop.payment.activity.PaymentActivity;
import com.hopop.hopop.ply.activity.PlyActivity;
import com.hopop.hopop.sidenavigation.mybooking.Data.BookingHisInfo;
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
}
