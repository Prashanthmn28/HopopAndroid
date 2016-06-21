package com.hopop.hopop.ply.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.ply.adapter.ServiceAdapter;
import com.hopop.hopop.payment.activity.PaymentActivity;
import com.hopop.hopop.ply.service.Service;
import com.hopop.hopop.source.activity.SourceActivity;

import java.util.ArrayList;
import java.util.List;

public class PlyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    TextView srcTxt,destTxt,row1,row2,row3;

    ListView mServiceListview;
    ServiceAdapter mAdapter;
    Toolbar toolbar;
    List<Service> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.PlyHeader);
        setContentView(R.layout.activity_ply);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        srcTxt = (TextView) findViewById(R.id.textView_pickpoint);
        destTxt = (TextView) findViewById(R.id.textView_droppoint);

       // String SRC = getIntent().getStringExtra("src");
        String SRC = SourceActivity.src;

       // System.out.println("Pickup point:"+SRC);

        String DST = getIntent().getStringExtra("dest");

        srcTxt.setText(SRC);
        destTxt.setText(DST);

        srcTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsrcTxt = new Intent(PlyActivity.this, SourceActivity.class);
                // intent_5.putExtra("src", srcSelect);
                startActivity(intentsrcTxt);

            }
        });
        destTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentdestTxt = new Intent(PlyActivity.this, SourceActivity.class);
                // intent_5.putExtra("src", srcSelect);
                startActivity(intentdestTxt);

            }
        });

        mServiceListview = (ListView) findViewById(R.id.listView);//.lv_service_availiability);

        mData.clear();
        mData.add(new Service("12 Seats Available", "Filling Fast", "In Next: <b>15min</b>", R.color.status_red, R.drawable.redbus));
        mData.add(new Service("25 Seats Available", "Slowly Filling", "In Next:  <b>40min</b>", R.color.status_orange, R.drawable.orangebus));
        mData.add(new Service("42 Seats Available", "Booking Started", "In Next:  <b>55min</b>", R.color.status_green, R.drawable.greenbus));

        mAdapter = new ServiceAdapter(this, mData);
        mServiceListview.setAdapter(mAdapter);

        mServiceListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentRow3 = new Intent(PlyActivity.this, PaymentActivity.class);
                startActivity(intentRow3);
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