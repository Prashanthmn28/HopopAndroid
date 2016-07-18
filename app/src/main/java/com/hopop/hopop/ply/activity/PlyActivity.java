package com.hopop.hopop.ply.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.database.SeatTimeList;
import com.hopop.hopop.destination.activity.DestinationActivity;
import com.hopop.hopop.destination.data.ForSeatAvailability;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.payment.activity.PaymentActivity;
import com.hopop.hopop.ply.adapter.DataAdapter;
import com.hopop.hopop.ply.data.SeatTimeInfo;
import com.hopop.hopop.sidenavigation.aboutus.AboutUs;
import com.hopop.hopop.sidenavigation.feedback.FeedBack;
import com.hopop.hopop.sidenavigation.mybooking.MyBooking;
import com.hopop.hopop.sidenavigation.notifications.Notifications;
import com.hopop.hopop.sidenavigation.profile.Profile;
import com.hopop.hopop.sidenavigation.suggestedroute.activity.SuggestedRoute;
import com.hopop.hopop.sidenavigation.suggestedroute.data.ForSuggestedRoute;
import com.hopop.hopop.sidenavigation.suggestedroute.data.SuggestedInfo;
import com.hopop.hopop.sidenavigation.wallet.Wallet;
import com.hopop.hopop.source.activity.SourceActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView srcTxt, destTxt, row1, row2, row3;
    Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<SeatTimeList> data;
    private DataAdapter adapter;
    public static String routeId = null;
    public static String tripId = null;
    public static String seats = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.PlyHeader);
        setContentView(R.layout.activity_ply);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //-----------post the parameters of from and to locations to server-----------------
        String src_point = SourceActivity.src;
        String src_pointId = SourceActivity.srcRId;
        String dest_point = DestinationActivity.destSelect;
        String dest_pointId = DestinationActivity.destSelectId;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String systime = sdf.format(new Date());

        Log.i(getClass().getSimpleName(), "Current Time:" + systime);

        final ForSeatAvailability forSeats = new ForSeatAvailability();
        forSeats.setSrc_stop(src_point);
        forSeats.setDest_stop(dest_point);
        forSeats.setUser_time(systime);
        CommunicatorClass.getRegisterClass().forSeatAvailiability(forSeats).enqueue(new Callback<SeatTimeInfo>() {
            @Override
            public void onResponse(Call<SeatTimeInfo> call, Response<SeatTimeInfo> response) {
                Log.e(getClass().getSimpleName(), "successful");
                final SeatTimeInfo sti = response.body();

                if (sti == null) {
                    srcTxt = (TextView) findViewById(R.id.textView_pickpoint);
                    destTxt = (TextView) findViewById(R.id.textView_droppoint);
                    String SRC = SourceActivity.src;
                    String DST = getIntent().getStringExtra("dest");
                    srcTxt.setText(SRC);
                    destTxt.setText(DST);
                    srcTxt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intentsrcTxt = new Intent(PlyActivity.this, SourceActivity.class);
                            startActivity(intentsrcTxt);

                        }
                    });
                    destTxt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intentdestTxt = new Intent(PlyActivity.this, SourceActivity.class);
                            startActivity(intentdestTxt);

                        }
                    });


                    TextView textView_NeedTrip = (TextView) findViewById(R.id.textView_needtrip);


                    textView_NeedTrip.setText("Need a trip around this time? Click Below");

                    TextView no_trip = (TextView) findViewById(R.id.textView_notrip);

                    no_trip.setText("No Trips Available.");

                    Button suggBtn = (Button) findViewById(R.id.button_suggest);
                    suggBtn.setVisibility(View.VISIBLE);

                    suggBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final ForSuggestedRoute forSuggestedRoute = new ForSuggestedRoute();
                            String userMobileNum = LoginActivity.usrMobileNum;
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                            String systime = sdf.format(new Date());

                            forSuggestedRoute.setMobile_number(userMobileNum);
                            forSuggestedRoute.setFrom_route(srcTxt.getText().toString());
                            forSuggestedRoute.setTo_route(destTxt.getText().toString());
                            forSuggestedRoute.getRequeste_on(systime);
                            CommunicatorClass.getRegisterClass().forRoute(forSuggestedRoute).enqueue(new Callback<SuggestedInfo>() {

                                @Override
                                public void onResponse(Call<SuggestedInfo> call, Response<SuggestedInfo> response) {

                                    Toast.makeText(PlyActivity.this, "The Suggested Route Add Successfully", Toast.LENGTH_LONG).show();

                                    Intent suggIntent = new Intent(PlyActivity.this, SourceActivity.class);
                                    startActivity(suggIntent);
                                }

                                @Override
                                public void onFailure(Call<SuggestedInfo> call, Throwable t) {

                                }
                            });
                        }
                    });


                } else {
                    //----------------DISPLAY THE NUMBER OF SEATS and MODE OF BUS IN UI------------------------

                    for (SeatTimeList stl : sti.getSeatTimeList()) {
                        Log.i(getClass().getSimpleName(), "Seats Available are " + stl.getSeatsAvailable());
                        Log.i(getClass().getSimpleName(), "Timings Available are " + stl.getTimeSlot());

                        routeId = stl.getRouteId();
                        tripId = stl.getTripId();

                    }

                    if (sti.getSeatTimeList() != null) {
                        Log.e(getClass().getSimpleName(), "The sorted list is " + sti.getSortedSeatTimeListByTime());
                        ArrayList<SeatTimeList> timeList = sti.getSortedSeatTimeListByTime();
                        adapter = new DataAdapter(timeList);
                        recyclerView.setAdapter(adapter);

                        ((DataAdapter) adapter).setOnItemClickListener(new DataAdapter.ItemClickListenr() {
                            @Override
                            public void onItemClick(int position, View v) {
                                Log.i(getClass().getSimpleName(), "the idem selected is " + sti.getSeatTimeList().get(position));

                                seats = sti.getSeatTimeList().get(position).getSeatsAvailable();

                                Intent intentSA = new Intent(PlyActivity.this, PaymentActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("seatDetails", sti.getSeatTimeList().get(position));
                                intentSA.putExtras(bundle);
                                startActivity(intentSA);
                            }
                        });

                    }


                }

            }

            @Override
            public void onFailure(Call<SeatTimeInfo> call, Throwable t) {
                Log.e(getClass().getSimpleName(), "failure");

            }


        });


        //------------------EOF POST THE FROM AND TO LOCATIONS----------------------------------

        //----------------DISPLAY THE FROM AND TO VALUES IN UI----------------------------------
        srcTxt = (TextView) findViewById(R.id.textView_pickpoint);
        destTxt = (TextView) findViewById(R.id.textView_droppoint);
        String SRC = SourceActivity.src;
        String DST = getIntent().getStringExtra("dest");
        srcTxt.setText(SRC);
        destTxt.setText(DST);
        srcTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsrcTxt = new Intent(PlyActivity.this, SourceActivity.class);
                startActivity(intentsrcTxt);

            }
        });
        destTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentdestTxt = new Intent(PlyActivity.this, SourceActivity.class);
                startActivity(intentdestTxt);

            }
        });
        //----------------EOF DISPLAY THE FROM AND TO VALUES IN UI----------------------------------

        //-------------------------SIDE NAVIGATION--------------------------------------
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //------------------------------EOF SIDE NAVIGATION--------------------------------

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {

            Intent profileintent = new Intent(PlyActivity.this, Profile.class);
            startActivity(profileintent);

        } else if (id == R.id.booking) {

            Intent bookingintent = new Intent(PlyActivity.this, MyBooking.class);
            startActivity(bookingintent);

        } else if (id == R.id.wallet) {

            Intent walletintent = new Intent(PlyActivity.this, Wallet.class);
            startActivity(walletintent);

        } else if (id == R.id.route) {

            Intent routeintent = new Intent(PlyActivity.this, SuggestedRoute.class);
            startActivity(routeintent);

        } else if (id == R.id.notification) {

            Intent notifyintent = new Intent(PlyActivity.this, Notifications.class);
            startActivity(notifyintent);

        } else if (id == R.id.feedback) {

            Intent feedbackintent = new Intent(PlyActivity.this, FeedBack.class);
            startActivity(feedbackintent);

        } else if (id == R.id.about) {

            Intent aboutintent = new Intent(PlyActivity.this, AboutUs.class);
            startActivity(aboutintent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {*/
            super.onBackPressed();
        //}
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen for landscape and portrait and set portrait mode always
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

}