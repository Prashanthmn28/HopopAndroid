package com.hopop.hopop.destination.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.database.FromRoute;

import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.ply.activity.PlyActivity;
import com.hopop.hopop.source.activity.SourceActivity;
import com.hopop.hopop.source.adapter.RecyclerAdapter;
import com.hopop.hopop.source.data.SourceList;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;

public class DestinationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;

    EditText search;

    //AutoCompleteTextView search;
    @Bind(R.id.source_list)
    RecyclerView source_list;
    private static final int TIME_DELAY = 3000;
    private static long back_pressed;
    public List<FromRoute> list1 = new ArrayList<>();
    public static String destSelect = null;
    RecyclerAdapter recyclerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.DropHeader);
        setContentView(R.layout.activity_destination);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        source_list.setLayoutManager(layoutManager);
        source_list.setItemAnimator(new DefaultItemAnimator());
        List<FromRoute> list1 = Select.from(FromRoute.class).list();
        for(FromRoute frmRout:list1){
            Log.e(getClass().getSimpleName(),"the db item is "+frmRout);
        }
        Bundle bundle = getIntent().getExtras();
        ArrayList<FromRoute> stopPoints = bundle.getParcelableArrayList("mylist");
        System.out.println("dest stop points:"+stopPoints);

        displayTheList(stopPoints);
        //post the parameters of from and to locations and stop id to server
        String src_point = SourceActivity.src;
        String src_point_id = SourceActivity.srcRId;
        String dest_point = DestinationActivity.destSelect;


        //  ForSeatAvailabilty seats = new ForSeatAvailabilty();


        //-----------------Navigation Bar-------------------------------
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


        source_list.setAdapter(recyclerAdapter);
        ((RecyclerAdapter)recyclerAdapter).setOnItemClickListener(new RecyclerAdapter.ItemClickListenr() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(getClass().getSimpleName(), "the item clicked is " + fromRoutes.get(position).getStopLocation());
                destSelect = fromRoutes.get(position).getStopLocation();
                Intent intent_6 = new Intent(DestinationActivity.this, PlyActivity.class);
                intent_6.putExtra("dest", destSelect);
                startActivity(intent_6);

            }
        });
    }
    public void addTextListener(){
        search.addTextChangedListener(new TextWatcher() {
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
                source_list.setLayoutManager(new LinearLayoutManager(DestinationActivity.this));
                recyclerAdapter = new RecyclerAdapter(fromRoutes, DestinationActivity.this);
                source_list.setAdapter(recyclerAdapter);
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

