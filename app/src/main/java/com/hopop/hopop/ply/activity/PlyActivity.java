package com.hopop.hopop.ply.activity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.database.ProfileInfo;
import com.hopop.hopop.database.SeatTimeList;
import com.hopop.hopop.destination.activity.DestinationActivity;
import com.hopop.hopop.destination.data.ForSeatAvailability;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.payment.activity.PaymentActivity;
import com.hopop.hopop.ply.adapter.DataAdapter;
import com.hopop.hopop.ply.data.SeatTimeInfo;
import com.hopop.hopop.registration.activity.RegisterActivity;
import com.hopop.hopop.sidenavigation.aboutus.activity.AboutUs;
import com.hopop.hopop.sidenavigation.feedback.activity.FeedBack;
import com.hopop.hopop.sidenavigation.mybooking.activity.MyBooking;
import com.hopop.hopop.sidenavigation.notifications.activity.Notifications;
import com.hopop.hopop.sidenavigation.profile.activity.Profile;
import com.hopop.hopop.sidenavigation.profile.adapter.ProfilePicAdapter;
import com.hopop.hopop.sidenavigation.suggestedroute.activity.SuggestedRoute;
import com.hopop.hopop.sidenavigation.suggestedroute.data.ForSuggestedRoute;
import com.hopop.hopop.sidenavigation.suggestedroute.data.SuggestedInfo;
import com.hopop.hopop.sidenavigation.wallet.activity.Wallet;
import com.hopop.hopop.source.activity.SourceActivity;
import com.hopop.hopop.source.data.ForProfileHeader;
import com.hopop.hopop.source.data.HeaderProfile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog progressDialog;
    TextView srcTxt, destTxt, row1, row2, row3;
    Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<SeatTimeList> data;
    private DataAdapter adapter;
    public static String routeId = null;
    public static String tripId = null;
    public static String seats = null;
    int pos_PrfPly;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.PlyHeader);
        setContentView(R.layout.activity_ply);
        //Initialize a LoadViewTask object and call the execute() method
        new LoadViewTask().execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //-----------post the parameters of from and to locations to server-----------------
        Bundle b = getIntent().getExtras();
        String src_point = b.getString("src");
        String src_pointId = SourceActivity.srcRId;
        String dest_point = DestinationActivity.destSelect;
        String dest_pointId = DestinationActivity.destSelectId;
        Intent bookinHistIntent = getIntent();
        src_point = bookinHistIntent.getExtras().getString("src");
        dest_point = bookinHistIntent.getExtras().getString("dest");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String systime = sdf.format(new Date());
        Log.i(getClass().getSimpleName(), "Current Time:" + systime);
        final ForSeatAvailability forSeats = new ForSeatAvailability();
        forSeats.setSrc_stop(src_point);
        forSeats.setDest_stop(dest_point);
        forSeats.setUser_time(systime);
        final String finalSrc_point = src_point;
        final String finalDest_point = dest_point;
        CommunicatorClass.getRegisterClass().forSeatAvailiability(forSeats).enqueue(new Callback<SeatTimeInfo>() {
            @Override
            public void onResponse(Call<SeatTimeInfo> call, Response<SeatTimeInfo> response) {
                Log.e(getClass().getSimpleName(), "successful");
                final SeatTimeInfo sti = response.body();
                if (sti == null) {
                    srcTxt = (TextView) findViewById(R.id.textView_pickpoint);
                    destTxt = (TextView) findViewById(R.id.textView_droppoint);
                    srcTxt.setText(finalSrc_point);
                    destTxt.setText(finalDest_point);
                    srcTxt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intentsrcTxt = new Intent(PlyActivity.this, SourceActivity.class);
                            intentsrcTxt.putExtras(bundle);
                            intentsrcTxt.putExtra("id",pos_PrfPly);
                               startActivity(intentsrcTxt);
                        }
                    });
                    destTxt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intentdestTxt = new Intent(PlyActivity.this, SourceActivity.class);
                            intentdestTxt.putExtras(bundle);
                            intentdestTxt.putExtra("id",pos_PrfPly);
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

                                bundle.putParcelable("seatDetails", sti.getSeatTimeList().get(position));
                                intentSA.putExtras(bundle);
                                intentSA.putExtra("prfPic",pos_PrfPly);
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
        //----------------DISPLAY THE FROM AND TO VALUES IN UI----------------------------------
        srcTxt = (TextView) findViewById(R.id.textView_pickpoint);
        destTxt = (TextView) findViewById(R.id.textView_droppoint);
        srcTxt.setText(finalSrc_point);
        destTxt.setText(finalDest_point);
        srcTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsrcTxt = new Intent(PlyActivity.this, SourceActivity.class);
                intentsrcTxt.putExtras(bundle);
                intentsrcTxt.putExtra("id",pos_PrfPly);
                startActivity(intentsrcTxt);
            }
        });
        destTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentdestTxt = new Intent(PlyActivity.this, SourceActivity.class);
                intentdestTxt.putExtras(bundle);
                intentdestTxt.putExtra("id",pos_PrfPly);
                startActivity(intentdestTxt);
            }
        });
        //-------------------------SIDE NAVIGATION--------------------------------------
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final View headView = navigationView.getHeaderView(0);
        final ImageView imgView = (ImageView) headView.findViewById(R.id.imageView);
        // get intent data
        Intent i = getIntent();
        // Selected image id
        pos_PrfPly = i.getExtras().getInt("prfPic");
        Log.i(getClass().getSimpleName(),"ImgPosition:"+pos_PrfPly);
        ProfilePicAdapter imageAdapter = new ProfilePicAdapter(this);
        imgView.setImageResource(imageAdapter.picArry[pos_PrfPly]);
        String lMob = LoginActivity.usrMobileNum;
        if(lMob == null)
        {
            String rMob = RegisterActivity.userMobNum;
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
                        TextView name = (TextView) headView.findViewById(R.id.textView_pName);
                        TextView mob = (TextView) headView.findViewById(R.id.textView_pMobile);
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
                        TextView name = (TextView) headView.findViewById(R.id.textView_pName);
                        TextView mob = (TextView) headView.findViewById(R.id.textView_pMobile);
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
            super.onBackPressed();
    }

    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
        //Before running code in separate thread
        @Override
        protected void onPreExecute()
        {
            //Create a new progress dialog
            progressDialog = new ProgressDialog(PlyActivity.this);
            progressDialog.setMessage("Loading...");
            //This dialog can't be canceled by pressing the back key
            progressDialog.setCancelable(false);
            //This dialog isn't indeterminate
            progressDialog.setIndeterminate(true);
            //The maximum number of items is 100
            progressDialog.setMax(100);
            //Set the current progress to zero
            progressDialog.setProgress(0);
            //Display the progress dialog
            progressDialog.show();
        }

        //The code to be executed in a background thread.
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                //Get the current thread's token
                synchronized (this)
                {
                    //Initialize an integer (that will act as a counter) to zero
                    int counter = 0;
                    //While the counter is smaller than four
                    while(counter <= 4)
                    {
                        //Wait 850 milliseconds
                        this.wait(500);
                        //Increment the counter
                        counter++;
                        //Set the current progress.
                        //This value is going to be passed to the onProgressUpdate() method.
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

        //Update the progress
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            //set the current progress of the progress dialog
            progressDialog.setProgress(values[0]);
        }

        //after executing the code in the thread
        @Override
        protected void onPostExecute(Void result)
        {
            //close the progress dialog
            progressDialog.dismiss();
            //initialize the View
            //setContentView(R.layout.content_booking);
        }
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