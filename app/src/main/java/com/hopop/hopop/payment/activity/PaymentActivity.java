package com.hopop.hopop.payment.activity;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.database.Wallet;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.login.data.LoginUser;
import com.hopop.hopop.payment.data.WalletInfo;
import com.hopop.hopop.source.activity.SourceActivity;
import com.hopop.hopop.login.activity.LoginActivity;

import javax.annotation.Nullable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;

    @Nullable
    @Bind(R.id.textView_justme)
    TextView MePlus;
    @Nullable @Bind(R.id.textView_justme1) TextView MePlus1;
    @Nullable @Bind(R.id.textView_justme2) TextView MePlus2;
    @Nullable @Bind(R.id.textView_justme3) TextView MePlus3;
    @Nullable @Bind(R.id.textView_NumofSeats) TextView numofSeats;
    @Nullable @Bind(R.id.textView_rideshareCalc) TextView rideshareCalc;
    @Nullable @Bind(R.id.textView_rideshareAmt) TextView rideshareAmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.PaymentHeader);
        setContentView(R.layout.activity_payment);
        try {
            ButterKnife.bind(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String userMobileNum = LoginActivity.usrMobileNum;
        Log.i(getClass().getSimpleName(),"UserLogin Mobile Number:"+userMobileNum);

       /* final WalletInfo walletInfo = new WalletInfo();
        walletInfo.setWallet();*/
        LoginUser loginUser  = new LoginUser();

        loginUser.setMobile_number(userMobileNum);

        CommunicatorClass.getRegisterClass().forWallet(loginUser).enqueue(new Callback<WalletInfo>() {
            @Override
            public void onResponse(Call<WalletInfo> call, Response<WalletInfo> response) {
                Toast.makeText(PaymentActivity.this,"Payment Activity Successfully",Toast.LENGTH_SHORT).show();

                Log.e(getClass().getSimpleName(),"Successfull");

                WalletInfo walletInfo = response.body();
                for (Wallet wallet:walletInfo.getWallet())
                {
                    Log.i(getClass().getSimpleName(),"user wallet is:"+wallet.getBalance().toString());

                    TextView balance = (TextView) findViewById(R.id.textView_BalanceAmt);

                    String bal = wallet.getBalance().toString();

                    balance.setText(bal+"Rs");
                }



            }

            @Override
            public void onFailure(Call<WalletInfo> call, Throwable t) {
                Log.i(getClass().getSimpleName(),"Failure");

            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @OnClick(R.id.textView_justme)
    public void mePlusUser(View view){
        numofSeats = (TextView)findViewById(R.id.textView_NumofSeats);
        numofSeats.setText("01");
        rideshareCalc = (TextView)findViewById(R.id.textView_rideshareCalc);
        rideshareCalc.setText("01X30");
        rideshareAmt = (TextView)findViewById(R.id.textView_rideshareAmt);
        rideshareAmt.setText("30Rs");
    }

    @OnClick(R.id.textView_justme1)
    public void mePlusUser1(View view){
        numofSeats = (TextView)findViewById(R.id.textView_NumofSeats);
        numofSeats.setText("02");
        rideshareCalc = (TextView)findViewById(R.id.textView_rideshareCalc);
        rideshareCalc.setText("02X30");
        rideshareAmt = (TextView)findViewById(R.id.textView_rideshareAmt);
        rideshareAmt.setText("60Rs");
    }

    @OnClick(R.id.textView_justme2)
    public void mePlusUser2(View view){
        numofSeats = (TextView)findViewById(R.id.textView_NumofSeats);
        numofSeats.setText("03");
        rideshareCalc = (TextView)findViewById(R.id.textView_rideshareCalc);
        rideshareCalc.setText("03X30");
        rideshareAmt = (TextView)findViewById(R.id.textView_rideshareAmt);
        rideshareAmt.setText("90Rs");
    }

    @OnClick(R.id.textView_justme3)
    public void mePlusUser3(View view){
        numofSeats = (TextView)findViewById(R.id.textView_NumofSeats);
        numofSeats.setText("04");
        rideshareCalc = (TextView)findViewById(R.id.textView_rideshareCalc);
        rideshareCalc.setText("04X30");
        rideshareAmt = (TextView)findViewById(R.id.textView_rideshareAmt);
        rideshareAmt.setText("120Rs");
    }

    @OnClick(R.id.button_Pay)
    public void payUser(View view){
        AlertDialog alertDialog = new AlertDialog.Builder(
                PaymentActivity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Ticket Confirmed");

        // Setting Dialog Message
        alertDialog.setMessage("Booking Id: 772");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.tick);

        // Setting OK Button
        alertDialog.setButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Intent intent_1 = new Intent(PaymentActivity.this, SourceActivity.class);
                        startActivity(intent_1);
                    }
                });

        // Showing Alert Message
        alertDialog.show();

        showNotification(PaymentActivity.this);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {

        } else if (id == R.id.booking) {

        } else if (id == R.id.wallet) {

        } else if (id == R.id.route) {

        } else if (id == R.id.notification) {

        } else if (id == R.id.feedback) {

        } else if (id == R.id.about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showConfirmationDialog(){
        new MaterialDialog.Builder(this)
                .title("Ticket Confirmed")
                .content("Booking Id: 772")
                .positiveText("ok")
                .show();
    }

    public static void showNotification(Context ctx) {
        String msg = "Its time to be there in boarding Point."
                + "\nBoarding pass:- KA0103020830AZH" + "\nSeat position:- 3S-W\n" +
                "Happy Journey!!!.Have a nice day.";

        Intent intent = new Intent(ctx, LoginActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(ctx);

        // Add Big View Specific Configuration
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText(msg).setBigContentTitle("Hopper").setSummaryText("Ticket confirmed");

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Ticket confirmed")
                .setContentTitle("Hopper")
                .setContentText(msg)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                //.setContentIntent(contentIntent)
                .setStyle(bigTextStyle);
        //.setContentInfo("Info");

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen for landscape and portrait and set portrait mode always
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}