package com.hopop.hopop.sidenavigation.suggestedroute;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hopop.hopop.login.activity.R;

public class SuggestedRoute extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestedroute);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    }
}