package com.hopop.hopop.payment.activity;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.database.BookId;
import com.hopop.hopop.database.ProfileInfo;
import com.hopop.hopop.database.Wallet;
import com.hopop.hopop.destination.activity.DestinationActivity;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.login.data.LoginUser;
import com.hopop.hopop.payment.data.BookIdInfo;
import com.hopop.hopop.payment.data.ForBookId;
import com.hopop.hopop.payment.data.WalletInfo;
import com.hopop.hopop.ply.activity.PlyActivity;
import com.hopop.hopop.registration.activity.RegisterActivity;
import com.hopop.hopop.sidenavigation.aboutus.activity.AboutUs;
import com.hopop.hopop.sidenavigation.feedback.activity.FeedBack;
import com.hopop.hopop.sidenavigation.mybooking.activity.MyBooking;
import com.hopop.hopop.sidenavigation.notifications.activity.Notifications;
import com.hopop.hopop.sidenavigation.profile.activity.Profile;
import com.hopop.hopop.sidenavigation.profile.adapter.ProfilePicAdapter;
import com.hopop.hopop.sidenavigation.suggestedroute.activity.SuggestedRoute;
import com.hopop.hopop.source.activity.SourceActivity;
import com.hopop.hopop.source.data.ForProfileHeader;
import com.hopop.hopop.source.data.HeaderProfile;

import javax.annotation.Nullable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ProgressDialog progressDialog;
    Toolbar toolbar;
    String[] bookidNum = {null};
    @Nullable
    @Bind(R.id.textView_justme)
    TextView MePlus;
    @Nullable @Bind(R.id.textView_justme1) TextView MePlus1;
    @Nullable @Bind(R.id.textView_justme2) TextView MePlus2;
    @Nullable @Bind(R.id.textView_justme3) TextView MePlus3;
    @Nullable @Bind(R.id.textView_NumofSeats) TextView numofSeats;
    @Nullable @Bind(R.id.textView_rideshareCalc) TextView rideshareCalc;
    @Nullable @Bind(R.id.textView_rideshareAmt) TextView rideshareAmt;
    int pos_PrfPay,nSeats;
    String frmSplMob,tripId;
    String lMob = LoginActivity.usrMobileNum;
    String rMob = RegisterActivity.userMobNum;



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
        new LoadViewTask().execute();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().getExtras()!=null) {
            pos_PrfPay = getIntent().getExtras().getInt("prfPic");
            Log.i(getClass().getSimpleName(),"ImgPosition:"+pos_PrfPay);
            frmSplMob = getIntent().getExtras().getString("lMob");
            tripId = getIntent().getExtras().getString("tripID");
            nSeats = getIntent().getExtras().getInt("nSeats");
        }

        if(nSeats <=1)
        {
            MePlus1.setEnabled(false);
            MePlus1.setClickable(false);
            MePlus1.setBackgroundColor(Color.TRANSPARENT);

            MePlus2.setEnabled(false);
            MePlus2.setClickable(false);
            MePlus2.setBackgroundColor(Color.TRANSPARENT);

            MePlus3.setEnabled(false);
            MePlus3.setClickable(false);
            MePlus3.setBackgroundColor(Color.TRANSPARENT);
        }
        if(nSeats <=2)
        {

            MePlus2.setEnabled(false);
            MePlus2.setClickable(false);
            MePlus2.setBackgroundColor(Color.TRANSPARENT);

            MePlus3.setEnabled(false);
            MePlus3.setClickable(false);
            MePlus3.setBackgroundColor(Color.TRANSPARENT);
        }
        if (nSeats<=3)
        {

            MePlus3.setEnabled(false);
            MePlus3.setClickable(false);
            MePlus3.setBackgroundColor(Color.TRANSPARENT);
        }

        if(lMob == null && rMob ==null)
        {
            LoginUser loginUser  = new LoginUser();
            loginUser.setMobile_number(frmSplMob);
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

        }
        else if (lMob == null)
        {
            LoginUser loginUser  = new LoginUser();
            loginUser.setMobile_number(rMob);
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
        }
        else
        {
            LoginUser loginUser  = new LoginUser();
            loginUser.setMobile_number(lMob);
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final View headView = navigationView.getHeaderView(0);
        final ImageView imgView = (ImageView) headView.findViewById(R.id.imageView);

        ProfilePicAdapter imageAdapter = new ProfilePicAdapter(this);
        imgView.setImageResource(imageAdapter.picArry[pos_PrfPay]);

        if(lMob == null && rMob == null)
        {
            final ForProfileHeader forProfileHeader =new ForProfileHeader();
            forProfileHeader.setMobile_number(frmSplMob);
            CommunicatorClass.getRegisterClass().headerProfile(forProfileHeader).enqueue(new Callback<HeaderProfile>() {
                @Override
                public void onResponse(Call<HeaderProfile> call, Response<HeaderProfile> response) {
                    HeaderProfile headerProfile = response.body();
                    for(ProfileInfo profileInfo:headerProfile.getProfileInfo())
                    {
                        View headView = navigationView.getHeaderView(0);
                        TextView name = (TextView) headView.findViewById(R.id.textView_pmName);
                        TextView mob = (TextView) headView.findViewById(R.id.textView_pmMobile);
                        mob.setText(profileInfo.getMobile_number());
                        name.setText(profileInfo.getUser_name());
                    }
                }
                @Override
                public void onFailure(Call<HeaderProfile> call, Throwable t) {
                    Log.i(getClass().getSimpleName(),"Failure Header Profile");
                }
            });
        }
       else if(lMob == null)
        {

            Log.i(getClass().getSimpleName(),"register Mobile Num:"+rMob);
            final ForProfileHeader forProfileHeader =new ForProfileHeader();
            forProfileHeader.setMobile_number(rMob);
            CommunicatorClass.getRegisterClass().headerProfile(forProfileHeader).enqueue(new Callback<HeaderProfile>() {
                @Override
                public void onResponse(Call<HeaderProfile> call, Response<HeaderProfile> response) {
                    HeaderProfile headerProfile = response.body();
                    for(ProfileInfo profileInfo:headerProfile.getProfileInfo())
                    {
                        View headView = navigationView.getHeaderView(0);
                        TextView name = (TextView) headView.findViewById(R.id.textView_pmName);
                        TextView mob = (TextView) headView.findViewById(R.id.textView_pmMobile);
                        name.setText(profileInfo.getUser_name());
                        mob.setText(profileInfo.getMobile_number());
                    }
                }
                @Override
                public void onFailure(Call<HeaderProfile> call, Throwable t) {
                    Log.i(getClass().getSimpleName(),"Failure Header Profile");
                }
            });
        }
        else {
            final ForProfileHeader forProfileHeader =new ForProfileHeader();
            forProfileHeader.setMobile_number(lMob);
            CommunicatorClass.getRegisterClass().headerProfile(forProfileHeader).enqueue(new Callback<HeaderProfile>() {
                @Override
                public void onResponse(Call<HeaderProfile> call, Response<HeaderProfile> response) {
                    HeaderProfile headerProfile = response.body();
                    for(ProfileInfo profileInfo:headerProfile.getProfileInfo())
                    {
                        View headView = navigationView.getHeaderView(0);
                        TextView name = (TextView) headView.findViewById(R.id.textView_pmName);
                        TextView mob = (TextView) headView.findViewById(R.id.textView_pmMobile);
                        name.setText(profileInfo.getUser_name());
                        mob.setText(profileInfo.getMobile_number());
                    }
                }
                @Override
                public void onFailure(Call<HeaderProfile> call, Throwable t) {
                    Log.i(getClass().getSimpleName(),"Failure Header Profile");
                }
            });
        }
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
        String frmRoute = SourceActivity.srcSelected;
        String toRoute = DestinationActivity.destSelect;
        String rid = PlyActivity.routeId;
        String tid = tripId;
        String num_seats = numofSeats.getText().toString();
        String amtPaid = rideshareAmt.getText().toString();
        final ForBookId forBookId = new ForBookId();

        if(lMob ==null && rMob ==null)
        {
            forBookId.setUser_mobile(frmSplMob);
            forBookId.setFrom_route(frmRoute);
            forBookId.setTo_route(toRoute);
            forBookId.setRoute_id(rid);
            forBookId.setTrip_id(tid);
            forBookId.setTotal_seats(num_seats);
            forBookId.setAmount_paid(amtPaid);
            CommunicatorClass.getRegisterClass().forBookIdInfo(forBookId).enqueue(new Callback<BookIdInfo>() {
                @Override
                public void onResponse(Call<BookIdInfo> call, Response<BookIdInfo> response) {
                    Log.e(getClass().getSimpleName(), "successful bookid");
                    BookIdInfo bookIdInfo = response.body();
                    for (BookId bookId: bookIdInfo.getBookId())
                    {
                        bookidNum[0] = bookId.getBookId().toString();
                        Log.i(getClass().getSimpleName(),"user BookId is:"+bookidNum[0]);

                    }
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            PaymentActivity.this).create();
                    alertDialog.setTitle("Ticket Confirmed");
                    alertDialog.setMessage("Booking Id:"+  bookidNum[0]);
                    alertDialog.setIcon(R.drawable.tick);
                    alertDialog.setButton("OK",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent intent_1 = new Intent(PaymentActivity.this, SourceActivity.class);
                                    intent_1.putExtra("id",pos_PrfPay);
                                    intent_1.putExtra("lMob",frmSplMob);
                                    startActivity(intent_1);
                                }
                            });
                    alertDialog.show();
                    showNotification(PaymentActivity.this);
                }
                @Override
                public void onFailure(Call<BookIdInfo> call, Throwable t) {
                }
            });
        }
        else if(lMob == null)
        {
            forBookId.setUser_mobile(rMob);
            forBookId.setFrom_route(frmRoute);
            forBookId.setTo_route(toRoute);
            forBookId.setRoute_id(rid);
            forBookId.setTrip_id(tid);
            forBookId.setTotal_seats(num_seats);
            forBookId.setAmount_paid(amtPaid);
            CommunicatorClass.getRegisterClass().forBookIdInfo(forBookId).enqueue(new Callback<BookIdInfo>() {
                @Override
                public void onResponse(Call<BookIdInfo> call, Response<BookIdInfo> response) {
                    Log.e(getClass().getSimpleName(), "successful bookid");
                    BookIdInfo bookIdInfo = response.body();
                    for (BookId bookId: bookIdInfo.getBookId())
                    {
                        bookidNum[0] = bookId.getBookId().toString();
                        Log.i(getClass().getSimpleName(),"user BookId is:"+bookidNum[0]);

                    }
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            PaymentActivity.this).create();
                    alertDialog.setTitle("Ticket Confirmed");
                    alertDialog.setMessage("Booking Id:"+  bookidNum[0]);
                    alertDialog.setIcon(R.drawable.tick);
                    alertDialog.setButton("OK",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent intent_1 = new Intent(PaymentActivity.this, SourceActivity.class);
                                    intent_1.putExtra("id",pos_PrfPay);
                                    intent_1.putExtra("lMob",frmSplMob);
                                    startActivity(intent_1);
                                }
                            });
                    alertDialog.show();
                    showNotification(PaymentActivity.this);
                }
                @Override
                public void onFailure(Call<BookIdInfo> call, Throwable t) {
                }
            });
        }
        else
        {
            forBookId.setUser_mobile(lMob);
            forBookId.setFrom_route(frmRoute);
            forBookId.setTo_route(toRoute);
            forBookId.setRoute_id(rid);
            forBookId.setTrip_id(tid);
            forBookId.setTotal_seats(num_seats);
            forBookId.setAmount_paid(amtPaid);
            CommunicatorClass.getRegisterClass().forBookIdInfo(forBookId).enqueue(new Callback<BookIdInfo>() {
                @Override
                public void onResponse(Call<BookIdInfo> call, Response<BookIdInfo> response) {
                    Log.e(getClass().getSimpleName(), "successful bookid");
                    BookIdInfo bookIdInfo = response.body();
                    for (BookId bookId: bookIdInfo.getBookId())
                    {
                        bookidNum[0] = bookId.getBookId().toString();
                        Log.i(getClass().getSimpleName(),"user BookId is:"+bookidNum[0]);
                    }
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            PaymentActivity.this).create();
                    alertDialog.setTitle("Ticket Confirmed");
                    alertDialog.setMessage("Booking Id:"+  bookidNum[0]);
                    alertDialog.setIcon(R.drawable.tick);
                    alertDialog.setButton("OK",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent intent_1 = new Intent(PaymentActivity.this, SourceActivity.class);
                                    intent_1.putExtra("id",pos_PrfPay);
                                    intent_1.putExtra("lMob",frmSplMob);
                                    startActivity(intent_1);
                                }
                            });
                    alertDialog.show();
                    showNotification(PaymentActivity.this);

                }
                @Override
                public void onFailure(Call<BookIdInfo> call, Throwable t) {
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile) {
            Intent profileintent = new Intent(PaymentActivity.this, Profile.class);
            startActivity(profileintent);
        } else if (id == R.id.booking) {
            Intent bookingintent = new Intent(PaymentActivity.this, MyBooking.class);
            startActivity(bookingintent);
        } else if (id == R.id.wallet) {
            Intent walletintent = new Intent(PaymentActivity.this, com.hopop.hopop.sidenavigation.wallet.activity.Wallet.class);
            startActivity(walletintent);
        } else if (id == R.id.route) {
            Intent routeintent = new Intent(PaymentActivity.this, SuggestedRoute.class);
            startActivity(routeintent);
        } else if (id == R.id.notification) {
            Intent notifyintent = new Intent(PaymentActivity.this, Notifications.class);
            startActivity(notifyintent);
        } else if (id == R.id.feedback) {
            Intent feedbackintent = new Intent(PaymentActivity.this, FeedBack.class);
            startActivity(feedbackintent);
        } else if (id == R.id.about) {
            Intent aboutintent = new Intent(PaymentActivity.this, AboutUs.class);
            startActivity(aboutintent);
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
                .setStyle(bigTextStyle);
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());
    }

    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(PaymentActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                synchronized (this)
                {
                    int counter = 0;
                    while(counter <= 4)
                    {
                        this.wait(500);
                        counter++;
                        publishProgress(counter*25);
                    }
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result)
        {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
