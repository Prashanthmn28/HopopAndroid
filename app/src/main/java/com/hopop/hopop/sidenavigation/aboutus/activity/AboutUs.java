package com.hopop.hopop.sidenavigation.aboutus.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.hopop.hopop.login.activity.R;

public class AboutUs extends AppCompatActivity {
    /*static Point size;
    static float density;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        setTitle("AboutUs");
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

        WebView view = (WebView) findViewById(R.id.webView);
        String text;
        text = "<html><body><p align=\"justify\">";
        text+= "At RedBeak, we all come here to solve the biggest problem in Travel Domain.\n" +
                "And yes!! We are Damn Serious about this stuff!!! Ever wondered!\n" +
                "Why Bangalore has the worst Traffic in India? It’s because Bangalore\n" +
                "lack in a good public and private Transportation system.Added to that\n" +
                "its not managed and organised properly. This problem planted a seed in\n" +
                "our minds which eventually started to grow and here we are right in front of You!!\n" +
                "You might think we have restricted ourselves to Bangalore... Nope!!!\n" +
                "The Tree always grows… We believe Bangalore is a good place to start.\n" +
                "We have a great team who are passionate about innovating things and also fun\n" +
                "and adventure loving. Finally, we dream of making India a better and a smart nation\n" +
                "where transportation will never become a problem for people. Well, you want to know\n" +
                "more about the things we are working on ??!!!.Just scroll down a bit...";
        text+= "</p></body></html>";
        view.loadData(text, "text/html", "utf-8");

    }
}
