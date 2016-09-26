package com.hopop.hopop.destination.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.database.FromRoute;

import com.hopop.hopop.database.ProfileInfo;
import com.hopop.hopop.destination.adapter.DestRecyclerAdapter;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.ply.activity.PlyActivity;
import com.hopop.hopop.registration.activity.RegisterActivity;
import com.hopop.hopop.sidenavigation.aboutus.activity.AboutUs;
import com.hopop.hopop.sidenavigation.feedback.activity.FeedBack;
import com.hopop.hopop.sidenavigation.mybooking.activity.MyBooking;
import com.hopop.hopop.sidenavigation.notifications.activity.Notifications;
import com.hopop.hopop.sidenavigation.profile.activity.Profile;
import com.hopop.hopop.sidenavigation.profile.adapter.ProfilePicAdapter;
import com.hopop.hopop.sidenavigation.suggestedroute.activity.SuggestedRoute;
import com.hopop.hopop.source.data.ForProfileHeader;
import com.hopop.hopop.source.data.HeaderProfile;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DestinationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ProgressDialog progressDialog;

    EditText destSearch;
    @Bind(R.id.dest_list)
    RecyclerView dest_list;
    DestRecyclerAdapter destRecyclerAdapter;
    private static final int TIME_DELAY = 3000;
    private static long back_pressed;
    public static String destSelect = null;
    public static String srcSelect = null;
    public static String destSelectId = null;
    List<FromRoute> list1;
    DestRecyclerAdapter recyclerAdapter;
    String frmSplMob;
    int pos_desPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setTitle(R.string.DropHeader);
        new LoadViewTask().execute();
        setContentView(R.layout.activity_destination);
        ButterKnife.bind(this);
        destSearch = (EditText) findViewById( R.id.destSearch);
        dest_list = (RecyclerView) findViewById(R.id.dest_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dest_list.setLayoutManager(layoutManager);
        dest_list.setItemAnimator(new DefaultItemAnimator());
        Bundle bundle = getIntent().getExtras();
        srcSelect = bundle.getString("src");
        ArrayList<FromRoute> stopPoints = bundle.getParcelableArrayList("mylist");
        list1 = stopPoints;
        //--------DISPLAY THE LIST OF STOP POINTS-----
        displayTheList(stopPoints);
        //------------FOR SEARCH Bar----
        addTextListener();
        //-----------------SIDE NAVIGATION WORK-------------------------------
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_dest);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_dest);
        final View headView = navigationView.getHeaderView(0);
        final ImageView imgView = (ImageView) headView.findViewById(R.id.imageView);
        if(getIntent().getExtras()!=null) {
            pos_desPic = getIntent().getExtras().getInt("prfPic");
            frmSplMob = getIntent().getExtras().getString("lMob");
            ProfilePicAdapter imageAdapter = new ProfilePicAdapter(this);
            imgView.setImageResource(imageAdapter.picArry[pos_desPic]);
        }

        String lMob = LoginActivity.usrMobileNum;
        String rMob = RegisterActivity.userMobNum;
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
                        TextView name = (TextView) headView.findViewById(R.id.textView_dName);
                        TextView mob = (TextView) headView.findViewById(R.id.textView_dMobile);
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
                        TextView name = (TextView) headView.findViewById(R.id.textView_dName);
                        TextView mob = (TextView) headView.findViewById(R.id.textView_dMobile);
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
                        TextView name = (TextView) headView.findViewById(R.id.textView_dName);
                        TextView mob = (TextView) headView.findViewById(R.id.textView_dMobile);
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
    private void displayTheList(final List<FromRoute> fromRoutes){
        recyclerAdapter = new DestRecyclerAdapter(fromRoutes, this);
        dest_list.setAdapter(recyclerAdapter);
        dest_list.setLayoutManager(new LinearLayoutManager(DestinationActivity.this));
        recyclerAdapter.setOnItemClickListener(new DestRecyclerAdapter.ItemClickListenr(){
            @Override
            public void onItemClick(int position, View v) {
                destSelect = recyclerAdapter.getFilteredItem(position).getStopLocation();
                destSelectId = fromRoutes.get(position).getRouteId();
                Intent destIntent = new Intent(DestinationActivity.this, PlyActivity.class);
                destIntent.putExtra("dest", destSelect);
                destIntent.putExtra("src",srcSelect);
                destIntent.putExtra("prfPic",pos_desPic);
                destIntent.putExtra("lMob",frmSplMob);
                startActivity(destIntent);
            }
        });
    }
    public void addTextListener(){
        destSearch.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence query, int start, int before, int count) {
                query = query.toString().toLowerCase();
                recyclerAdapter.getFilter().filter(query);
            }
        });
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile) {
            Intent profileintent = new Intent(DestinationActivity.this, Profile.class);
            startActivity(profileintent);
        } else if (id == R.id.booking) {
            Intent bookingintent = new Intent(DestinationActivity.this, MyBooking.class);
            startActivity(bookingintent);
        } else if (id == R.id.wallet) {
            Intent walletintent = new Intent(DestinationActivity.this, com.hopop.hopop.sidenavigation.wallet.activity.Wallet.class);
            startActivity(walletintent);
        } else if (id == R.id.route) {
            Intent routeintent = new Intent(DestinationActivity.this, SuggestedRoute.class);
            startActivity(routeintent);
        } else if (id == R.id.notification) {
            Intent notifyintent = new Intent(DestinationActivity.this, Notifications.class);
            startActivity(notifyintent);
        } else if (id == R.id.feedback) {
            Intent feedbackintent = new Intent(DestinationActivity.this, FeedBack.class);
            startActivity(feedbackintent);
        } else if (id == R.id.about) {
            Intent aboutintent = new Intent(DestinationActivity.this, AboutUs.class);
            startActivity(aboutintent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(DestinationActivity.this);
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

