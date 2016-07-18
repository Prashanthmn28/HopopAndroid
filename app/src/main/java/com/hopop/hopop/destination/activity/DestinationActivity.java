package com.hopop.hopop.destination.activity;

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

import com.facebook.stetho.Stetho;
import com.hopop.hopop.database.FromRoute;

import com.hopop.hopop.destination.adapter.DestRecyclerAdapter;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.ply.activity.PlyActivity;
import com.hopop.hopop.sidenavigation.aboutus.AboutUs;
import com.hopop.hopop.sidenavigation.feedback.FeedBack;
import com.hopop.hopop.sidenavigation.mybooking.MyBooking;
import com.hopop.hopop.sidenavigation.notifications.Notifications;
import com.hopop.hopop.sidenavigation.profile.Profile;
import com.hopop.hopop.sidenavigation.suggestedroute.activity.SuggestedRoute;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DestinationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    EditText destSearch;
    @Bind(R.id.dest_list)
    RecyclerView dest_list;
    DestRecyclerAdapter destRecyclerAdapter;
    private static final int TIME_DELAY = 3000;
    private static long back_pressed;
    public static String destSelect = null;
    public static String destSelectId = null;
    List<FromRoute> list1;
    DestRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setTitle(R.string.DropHeader);
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
        ArrayList<FromRoute> stopPoints = bundle.getParcelableArrayList("mylist");
        list1 = stopPoints;
        //--------DISPLAY THE LIST OF STOP POINTS-----
        displayTheList(stopPoints);
        //--------EOF --------------------------------

        //------------FOR SEARCH--------
        addTextListener();
        //-----------EOF SEARCH--------

        //-----------------SIDE NAVIGATION WORK-------------------------------
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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

            Intent walletintent = new Intent(DestinationActivity.this, com.hopop.hopop.sidenavigation.wallet.Wallet.class);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

