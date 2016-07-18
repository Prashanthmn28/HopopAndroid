package com.hopop.hopop.sidenavigation.suggestedroute.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.sidenavigation.suggestedroute.data.ForSuggestedRoute;
import com.hopop.hopop.sidenavigation.suggestedroute.data.SuggestedInfo;
import com.hopop.hopop.source.activity.SourceActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestedRoute extends AppCompatActivity {
    EditText pkupPoint, drpPoint;
    Button suggesRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestedroute);
        ButterKnife.bind(this);
        setTitle("Suggest Routes");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_keyboard_backspace_white_48px));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pkupPoint = (EditText) findViewById(R.id.editText_Src);
        drpPoint = (EditText) findViewById(R.id.editText_Drp);

        suggesRoute = (Button) findViewById(R.id.button);

        suggesRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkFieldValidation()) {
                    final ForSuggestedRoute forSuggestedRoute = new ForSuggestedRoute();
                    String userMobileNum = LoginActivity.usrMobileNum;
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String systime = sdf.format(new Date());

                    forSuggestedRoute.setMobile_number(userMobileNum);
                    forSuggestedRoute.setFrom_route(pkupPoint.getText().toString());
                    forSuggestedRoute.setTo_route(drpPoint.getText().toString());
                    forSuggestedRoute.getRequeste_on(systime);
                    CommunicatorClass.getRegisterClass().forRoute(forSuggestedRoute).enqueue(new Callback<SuggestedInfo>() {

                        @Override
                        public void onResponse(Call<SuggestedInfo> call, Response<SuggestedInfo> response) {

                            Toast.makeText(SuggestedRoute.this, "The Request has been Accepted", Toast.LENGTH_LONG).show();

                            Intent suggIntent = new Intent(SuggestedRoute.this, SourceActivity.class);
                            startActivity(suggIntent);
                        }

                        @Override
                        public void onFailure(Call<SuggestedInfo> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    boolean valid = true;

    private boolean checkFieldValidation() {


        if (pkupPoint.length() == 0) {
            pkupPoint.requestFocus();
            pkupPoint.setError("Source Point is required.");
            valid = false;
        } else if (drpPoint.length() == 0) {
            drpPoint.requestFocus();
            drpPoint.setError("Drop Point is required.");
            valid = false;
        }

        return valid;
    }
}