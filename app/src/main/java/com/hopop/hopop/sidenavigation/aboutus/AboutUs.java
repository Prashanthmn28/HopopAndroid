package com.hopop.hopop.sidenavigation.aboutus;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.sidenavigation.aboutus.Justify.TextJustification;

public class AboutUs extends AppCompatActivity {
    static Point size;
    static float density;

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

        Display display = getWindowManager().getDefaultDisplay();
        size = new Point();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        density = dm.density;
        display.getSize(size);


        TextView tv = (TextView) findViewById(R.id.textView);
        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "Roboto-Medium.ttf");
        tv.setTypeface(typeface);
        tv.setLineSpacing(0f, 1.2f);
        tv.setTextSize(20 * AboutUs.density);

        //some random long text
        String myText = getResources().getString(R.string.about);

        tv.setText(myText);
        TextJustification.justify(tv, size.x);


    }
}
