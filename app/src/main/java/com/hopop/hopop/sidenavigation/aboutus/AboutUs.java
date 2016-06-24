package com.hopop.hopop.sidenavigation.aboutus;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.hopop.hopop.login.activity.R;

public class AboutUs extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        String htmlText = "<html><body style=\"text-align:justify\"> %s </body></Html>";
        String myData = "At RedBeak, we all come here to solve the biggest problem in Travel Domain. And yes!! " +
                "We are Damn Serious about this stuff!!! Ever wondered! Why Bangalore has the worst Traffic in India? " +
                "It’s because Bangalore lack in a good public and private Transportation system.Added to that its not " +
                "managed and organised properly. This problem planted a seed in our minds which eventually started to " +
                "grow and here we are right in front of You!! You might think we have restricted ourselves to " +
                "Bangalore... Nope!!! The Tree always grows… We believe Bangalore is a good place to start. We have a " +
                "great team who are passionate about innovating things and also fun and adventure loving. Finally, " +
                "we dream of making India a better and a smart nation where transportation will never become a problem " +
                "for people. Well, you want to know more about the things we are working on ??!!!.Just scroll down a bit...";

        WebView webView = (WebView) findViewById(R.id.webView1);
        webView.loadData(String.format(htmlText, myData), "text/html", "utf-8");
    }
}
