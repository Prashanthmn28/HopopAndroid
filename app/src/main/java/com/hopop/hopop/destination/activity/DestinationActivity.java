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

    int pos_desPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setTitle(R.string.DropHeader);
        //Initialize a LoadViewTask object and call the execute() method
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
         // get intent data
        Intent i = getIntent();

        // Selected image id
         pos_desPic = i.getExtras().getInt("prfPic");
        Log.i(getClass().getSimpleName(),"ImgPosition:"+pos_desPic);
        ProfilePicAdapter imageAdapter = new ProfilePicAdapter(this);
        imgView.setImageResource(imageAdapter.picArry[pos_desPic]);


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
                Log.i(getClass().getSimpleName(), "the item clicked is " + fromRoutes.get(position).getStopLocation());

                destSelect = recyclerAdapter.getFilteredItem(position).getStopLocation();
                // destSelect = fromRoutes.get(position).getStopLocation();
                destSelectId = fromRoutes.get(position).getRouteId();

                Intent destIntent = new Intent(DestinationActivity.this, PlyActivity.class);
                destIntent.putExtra("dest", destSelect);
                destIntent.putExtra("src",srcSelect);
                destIntent.putExtra("prfPic",pos_desPic);
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
        // Handle navigation view item clicks here.
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
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        super.onBackPressed();
    }

    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
        //Before running code in separate thread
        @Override
        protected void onPreExecute()
        {
            //Create a new progress dialog
            progressDialog = new ProgressDialog(DestinationActivity.this);
            //Set the dialog title to 'Loading...'
            //progressDialog.setTitle("Loading...");
            //Set the dialog message to 'Loading application View, please wait...'
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
            /* This is just a code that delays the thread execution 4 times,
             * during 850 milliseconds and updates the current progress. This
             * is where the code that is going to be executed on a background
             * thread must be placed.
             */
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
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

}

