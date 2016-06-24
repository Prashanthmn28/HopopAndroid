package com.hopop.hopop.sidenavigation.mybooking;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hopop.hopop.login.activity.R;

public class MyBooking extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybooking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    }
}