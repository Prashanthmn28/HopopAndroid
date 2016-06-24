package com.hopop.hopop.sidenavigation.notifications;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hopop.hopop.login.activity.R;

public class Notifications extends Activity

{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    }
}
