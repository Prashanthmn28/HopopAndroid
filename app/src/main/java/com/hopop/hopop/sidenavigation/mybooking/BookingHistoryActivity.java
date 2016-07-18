package com.hopop.hopop.sidenavigation.mybooking;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.login.data.LoginUser;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingHistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_past);
        setTitle("Booking History");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
/*

        String mob = LoginActivity.usrMobileNum;

        LoginUser loginUser = new LoginUser();

        loginUser.setMobile_number(mob);

        CommunicatorClass.getRegisterClass().bookingHis(loginUser).enqueue(new Callback<BookingHisInfo>() {
            @Override
            public void onResponse(Call<BookingHisInfo> call, Response<BookingHisInfo> response) {

                BookingHisInfo bookingHisInfo = response.body();


                for(BookingHistory bookingHistory:bookingHisInfo.getBookingHistory())
                {
                    EditText etDaate = (EditText)findViewById(R.id.editText_date);

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    SimpleDateFormat sdfD = new SimpleDateFormat("dd:mm:yyyy");
                    String systime = sdf.format(new Date());
                    String sysDate = sdfD.format(new Date());


                    etDaate.setText(sysDate);

                    EditText etFrm = (EditText)findViewById(R.id.editText_Src);


                    etFrm.setText("");

                    EditText etTo = (EditText)findViewById(R.id.editText_Drp);

                    etTo.setText(bookingHistory.getToLocation().toString());

                    TextView etTime = (TextView) findViewById(R.id.textView_time);


                    etTime.setText(systime);



                }
            }

            @Override
            public void onFailure(Call<BookingHisInfo> call, Throwable t) {

            }
        });
*/





    }


}
