package com.hopop.hopop.sidenavigation.mybooking.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hopop.hopop.login.activity.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyBooking extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybooking);
        setTitle("Booking History");
        ButterKnife.bind(this);
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


    }
    @Bind(R.id.imageView4)   ImageView mapBus;
    @Bind(R.id.textView_ride) TextView ride;

    @Bind(R.id.textView_booknow) TextView bookNow;

    @OnClick(R.id.button_history)
    public void pastUser (View view){


        Intent bhIntent = new Intent(MyBooking.this,BookingHistoryActivity.class);
        startActivity(bhIntent);

    }

}