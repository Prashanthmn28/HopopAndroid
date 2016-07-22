package com.hopop.hopop.sidenavigation.feedback.Activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hopop.hopop.login.activity.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedBack extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle("FeedBack");
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

        information.setText("Please provide additional information or suggestions for improvement");

    }

    @Bind(R.id.textView_rate) TextView trip;
    @Bind(R.id.ratingBar) RatingBar ratingBar;
    @Bind(R.id.textView_information) TextView information;
    @Bind(R.id.edittText_comment) EditText comments;
    @OnClick(R.id.button_send)
    public void sendUser (View view){

    }

    @OnClick(R.id.button_cancel)
    public void cancelUser (View view){

    }

}