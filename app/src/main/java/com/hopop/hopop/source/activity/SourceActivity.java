package com.hopop.hopop.source.activity;

import android.os.Bundle;
import android.os.Parcelable;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.database.FromRoute;
import com.hopop.hopop.destination.activity.DestinationActivity;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.sidenavigation.aboutus.AboutUs;
import com.hopop.hopop.sidenavigation.feedback.FeedBack;
import com.hopop.hopop.sidenavigation.mybooking.MyBooking;
import com.hopop.hopop.sidenavigation.notifications.Notifications;
import com.hopop.hopop.sidenavigation.profile.Profile;
import com.hopop.hopop.sidenavigation.suggestedroute.activity.SuggestedRoute;
import com.hopop.hopop.sidenavigation.wallet.Wallet;
import com.hopop.hopop.source.adapter.SrcRecyclerAdapter;
import com.hopop.hopop.source.data.SourceList;
import com.orm.query.Condition;
import com.orm.query.Select;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText search;
    Toolbar toolbar;
    private static final int TIME_DELAY = 3000;
    private static long back_pressed;
    public static String src = null,srcRId=null;
    @Bind(R.id.source_list)
    RecyclerView source_list;
    SrcRecyclerAdapter recyclerAdapter;
    public List<FromRoute> list1 = new ArrayList<>();
    public List<FromRoute> listItems = new ArrayList<>();

    List<FromRoute> tempDestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.PickHeader);
        setContentView(R.layout.activity_source);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        search = (EditText) findViewById( R.id.search);
        source_list = (RecyclerView) findViewById(R.id.source_list);
        final Call<SourceList> sourceList1 = CommunicatorClass.getRegisterClass().groupListSrc();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        source_list.setLayoutManager(layoutManager);
        source_list.setItemAnimator(new DefaultItemAnimator());
        sourceList1.enqueue(new Callback<SourceList>() {
            @Override
            public void onResponse(Call<SourceList> call, Response<SourceList> response) {
                Toast.makeText(SourceActivity.this, "In source Activity Login SuccessFully", Toast.LENGTH_SHORT).show();
                SourceList sl = response.body();
                for(FromRoute fromRoute: sl.getFromRoutes()){
                    if(FromRoute.isNew(fromRoute.getStopId())) {
                        fromRoute.save();
                    }
                }
                list1 = Select.from(FromRoute.class).list();
                for(FromRoute frmRout:list1){
                }
                listItems = FromRoute.findWithQuery(FromRoute.class,"Select * from FROM_ROUTE Group By stop_location");
                for(FromRoute fromRoute: listItems){
                    Log.i(getClass().getSimpleName(),"the stops are "+fromRoute.getStopLocation());
                }

                displayTheList(listItems);
                addTextListener();
            }
            @Override
            public void onFailure(Call<SourceList> call, Throwable t) {
                Toast.makeText(SourceActivity.this, "Invalid Mobile Number/Password", Toast.LENGTH_SHORT).show();
            }
        });
//-------------SIDE NAVIGATION --------------------------------------------------------
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        //  drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    List<FromRoute> startingPoint = null;
    private void displayTheList(final List<FromRoute> fromRoutes){
        recyclerAdapter = new SrcRecyclerAdapter(fromRoutes,getApplicationContext());
        source_list.setAdapter(recyclerAdapter);
        ((SrcRecyclerAdapter)recyclerAdapter).setOnItemClickListener(new SrcRecyclerAdapter.ItemClickListenr() {

            @Override
            public void onItemClick(int position, View v) {
                Log.i(getClass().getSimpleName(),"the item clicked is "+fromRoutes.get(position).getStopLocation());
                src = recyclerAdapter.getFilteredItem(position).getStopLocation();
                startingPoint = Select.from(FromRoute.class).where(Condition.prop("stop_location").eq(src)).list();
                for (FromRoute fromRoute: startingPoint) {
                    Log.i(getClass().getSimpleName(),"starting point:" + fromRoute.getRouteId());
                }

                //to get the stop points with the rout id(s) and the item not equal to the selected stop point
                List<FromRoute> destinationPoint = new ArrayList<FromRoute>();
                destList(destinationPoint);
                for(FromRoute fromRoute:destinationPoint) {
                    Log.i(getClass().getSimpleName(), "the destination point are "+fromRoute.getStopLocation());
                }
                Intent intentSrc = new Intent(SourceActivity.this, DestinationActivity.class);
                intentSrc.putExtra("src", src);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("mylist", (ArrayList<? extends Parcelable>) destinationPoint);
                intentSrc.putExtras(bundle);
                startActivity(intentSrc);
            }
        });
    }

    private void destList(List<FromRoute> destinationPoint) {

        for(FromRoute fromRoute:startingPoint) {
            tempDestList = FromRoute.findWithQuery(FromRoute.class, "Select * from FROM_ROUTE where route_id = ? AND stop_location != ? Group By stop_location", fromRoute.getRouteId(), src);
            if (destinationPoint.isEmpty()) {
                destinationPoint.addAll(tempDestList);
            } else {
                for (FromRoute tempDestRoute : tempDestList) {
                    boolean isUniqueRoute = false;
                    FromRoute uniqueRoute = null;
                    for (FromRoute destpoint : destinationPoint) {
                        if (!tempDestRoute.getStopLocation().equals(destpoint.getStopLocation())) {
                            isUniqueRoute = true;
                            uniqueRoute = tempDestRoute;
                        } else {
                            isUniqueRoute = false;
                            uniqueRoute = null;
                            break;
                        }
                    }
                    if (isUniqueRoute) {
                        if (uniqueRoute != null)
                            destinationPoint.add(uniqueRoute);
                    }
                }
            }
        }

    }

    public void addTextListener(){

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();
                final List<FromRoute> fromRoutes = new ArrayList<>();
                for (int i = 0; i < listItems.size(); i++) {
                    final String text = listItems.get(i).toString().toLowerCase();
                    if (text.contains(query)) {
                        fromRoutes.add(listItems.get(i));
                    }
                }
                source_list.setLayoutManager(new LinearLayoutManager(SourceActivity.this));
                recyclerAdapter = new SrcRecyclerAdapter(fromRoutes, SourceActivity.this);
                source_list.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();  // data set changed
            }
        });
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

        Intent intent = new Intent(SourceActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // clears all previous activities task
        finish(); // destroy current activity
        startActivity(intent); // starts new activity

    }


    //@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {

            Intent profileintent = new Intent(SourceActivity.this, Profile.class);
            startActivity(profileintent);

        } else if (id == R.id.booking) {

            Intent bookingintent = new Intent(SourceActivity.this, MyBooking.class);
            startActivity(bookingintent);

        } else if (id == R.id.wallet) {

            Intent walletintent = new Intent(SourceActivity.this, Wallet.class);
            startActivity(walletintent);

        } else if (id == R.id.route) {

            Intent routeintent = new Intent(SourceActivity.this, SuggestedRoute.class);
            startActivity(routeintent);

        } else if (id == R.id.notification) {

            Intent notifyintent = new Intent(SourceActivity.this, Notifications.class);
            startActivity(notifyintent);

        } else if (id == R.id.feedback) {

            Intent feedbackintent = new Intent(SourceActivity.this, FeedBack.class);
            startActivity(feedbackintent);

        } else if (id == R.id.about) {

            Intent aboutintent = new Intent(SourceActivity.this, AboutUs.class);
            startActivity(aboutintent);

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