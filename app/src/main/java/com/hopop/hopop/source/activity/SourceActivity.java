package com.hopop.hopop.source.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.database.FromRoute;
import com.hopop.hopop.database.ProfileInfo;
import com.hopop.hopop.destination.activity.DestinationActivity;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.registration.activity.RegisterActivity;
import com.hopop.hopop.sidenavigation.aboutus.activity.AboutUs;
import com.hopop.hopop.sidenavigation.feedback.activity.FeedBack;
import com.hopop.hopop.sidenavigation.mybooking.activity.MyBooking;
import com.hopop.hopop.sidenavigation.notifications.activity.Notifications;
import com.hopop.hopop.sidenavigation.profile.activity.GridImgActivity;
import com.hopop.hopop.sidenavigation.profile.activity.Profile;
import com.hopop.hopop.sidenavigation.profile.adapter.ProfilePicAdapter;
import com.hopop.hopop.sidenavigation.suggestedroute.activity.SuggestedRoute;
import com.hopop.hopop.sidenavigation.wallet.activity.Wallet;
import com.hopop.hopop.source.adapter.SrcRecyclerAdapter;
import com.hopop.hopop.source.data.ForProfileHeader;
import com.hopop.hopop.source.data.HeaderProfile;
import com.hopop.hopop.source.data.SourceList;
import com.orm.query.Condition;
import com.orm.query.Select;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog progressDialog;
    Toolbar toolbar;
    private static final int TIME_DELAY = 3000;
    private static long back_pressed;
    public static String srcSelected = null,srcRId=null;
    SrcRecyclerAdapter recyclerAdapter;
    public List<FromRoute> list1 = new ArrayList<>();
    public List<FromRoute> listItems = new ArrayList<>();
    List<FromRoute> tempDestList;

    @Bind(R.id.source_list)
    RecyclerView source_list;
    @Nullable @Bind(R.id.search)
    EditText search;
    @Nullable @Bind(R.id.textView_sMobile)
    TextView number;
    @Nullable @Bind(R.id.textView_sName)
    TextView name;
    int pos_prfPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.PickHeader);
        setContentView(R.layout.activity_source);
        ButterKnife.bind(this);
        //Initialize a LoadViewTask object and call the execute() method
        new LoadViewTask().execute();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        source_list = (RecyclerView) findViewById(R.id.source_list);
        final Call<SourceList> sourceList1 = CommunicatorClass.getRegisterClass().groupListSrc();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        source_list.setLayoutManager(layoutManager);
        source_list.setItemAnimator(new DefaultItemAnimator());
        sourceList1.enqueue(new Callback<SourceList>() {
            @Override
            public void onResponse(Call<SourceList> call, Response<SourceList> response) {
               // Toast.makeText(SourceActivity.this, "In source Activity Login SuccessFully", Toast.LENGTH_SHORT).show();
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
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final View headView = navigationView.getHeaderView(0);
        final ImageView imgView = (ImageView) headView.findViewById(R.id.imageView);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gridIntent = new Intent(getApplicationContext(),GridImgActivity.class);
                startActivity(gridIntent);
            }
        });
//---------for profilePic------------------
        if(getIntent().getExtras()!=null) {
            pos_prfPic = getIntent().getExtras().getInt("id");
            Log.i(getClass().getSimpleName(),"srcImgPrf:"+pos_prfPic);
            ProfilePicAdapter imageAdapter = new ProfilePicAdapter(this);
            imgView.setImageResource(imageAdapter.picArry[pos_prfPic]);
        }

//-------------------for profileName nd Number-------------
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
                        TextView name = (TextView) headView.findViewById(R.id.textView_sName);
                        TextView mob = (TextView) headView.findViewById(R.id.textView_sMobile);
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
                        TextView name = (TextView) headView.findViewById(R.id.textView_sName);
                        TextView mob = (TextView) headView.findViewById(R.id.textView_sMobile);
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
        navigationView.setNavigationItemSelectedListener(this);
    }//eof onCreate()

    List<FromRoute> startingPoint = null;
    private void displayTheList(final List<FromRoute> fromRoutes){
        recyclerAdapter = new SrcRecyclerAdapter(fromRoutes,getApplicationContext());
        source_list.setAdapter(recyclerAdapter);
        ((SrcRecyclerAdapter)recyclerAdapter).setOnItemClickListener(new SrcRecyclerAdapter.ItemClickListenr() {

            @Override
            public void onItemClick(int position, View v) {
                Log.i(getClass().getSimpleName(),"the item clicked is "+fromRoutes.get(position).getStopLocation());
                srcSelected = recyclerAdapter.getFilteredItem(position).getStopLocation();
                startingPoint = Select.from(FromRoute.class).where(Condition.prop("stop_location").eq(srcSelected)).list();
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
                intentSrc.putExtra("src", srcSelected);
                intentSrc.putExtra("prfPic",pos_prfPic);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("mylist", (ArrayList<? extends Parcelable>) destinationPoint);
                intentSrc.putExtras(bundle);
                startActivity(intentSrc);
            }
        });
    }//eof displayTheList

    private void destList(List<FromRoute> destinationPoint) {
       for(FromRoute fromRoute:startingPoint) {
            tempDestList = FromRoute.findWithQuery(FromRoute.class, "Select * from FROM_ROUTE where route_id = ? AND stop_location != ? Group By stop_location", fromRoute.getRouteId(), srcSelected);
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
    }//eof destList()

    public void addTextListener(){
        if (search != null) {
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
    }//eof addTextListener

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
    }//eof onBackPressed

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
    }//eof onNavigationItemSelected

    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
        //Before running code in separate thread
        @Override
        protected void onPreExecute()
        {
            //Create a new progress dialog
            progressDialog = new ProgressDialog(SourceActivity.this);
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