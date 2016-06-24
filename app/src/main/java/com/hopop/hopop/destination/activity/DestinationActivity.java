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
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.hopop.hopop.database.FromRoute;

import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.ply.activity.PlyActivity;
import com.hopop.hopop.source.activity.SourceActivity;
import com.hopop.hopop.source.adapter.RecyclerAdapter;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DestinationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    EditText destSearch;
    // @Bind(R.id.dest_list)
    RecyclerView dest_list;
    RecyclerAdapter recyclerAdapter;
    private static final int TIME_DELAY = 3000;
    private static long back_pressed;
    public List<FromRoute> list1 = new ArrayList<>();
    public static String destSelect = null;
    public static String destSelectId = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setTitle(R.string.DropHeader);
        setContentView(R.layout.activity_destination);
        destSearch = (EditText) findViewById( R.id.destSearch);
        dest_list = (RecyclerView) findViewById(R.id.dest_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dest_list.setLayoutManager(layoutManager);
        dest_list.setItemAnimator(new DefaultItemAnimator());
        List<FromRoute> list1 = Select.from(FromRoute.class).list();
        for(FromRoute frmRout:list1){
            //  Log.e(getClass().getSimpleName(),"the db item is "+frmRout);
        }
        Bundle bundle = getIntent().getExtras();
        ArrayList<FromRoute> stopPoints = bundle.getParcelableArrayList("mylist");

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
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(fromRoutes,getApplicationContext());
        dest_list.setAdapter(recyclerAdapter);
        ((RecyclerAdapter)recyclerAdapter).setOnItemClickListener(new RecyclerAdapter.ItemClickListenr() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(getClass().getSimpleName(), "the item clicked is " + fromRoutes.get(position).getStopLocation());
                destSelect = fromRoutes.get(position).getStopLocation();
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
                final List<FromRoute> fromRoutes = new ArrayList<>();
                for (int i = 0; i < list1.size(); i++) {
                    final String text = list1.get(i).toString().toLowerCase();
                    if (text.contains(query)) {
                        fromRoutes.add(list1.get(i));
                    }
                }
                dest_list.setLayoutManager(new LinearLayoutManager(DestinationActivity.this));
                recyclerAdapter = new RecyclerAdapter(fromRoutes, DestinationActivity.this);
                dest_list.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();  // data set changed
            }
        });
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();

        Intent intent = new Intent(DestinationActivity.this, SourceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // clears all previous activities task
        finish(); // destroy current activity
        startActivity(intent); // starts new activity

    }
}

