package com.hopop.hopop.registration.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class UserProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String host = "api.linkedin.com";
    private static final String host1 = "in.linkedin.com/";
    private static final String topCardUrl = "https://" + host + "/v1/people/~:(email-address,formatted-name,phone-numbers,public-profile-url,picture-url,picture-urls::(original))";
    ProgressDialog progress;
    ImageView profile_pic;
    TextView user_name,user_mobile;
    NavigationView navigation_view;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        logout = (Button) findViewById(R.id.button_logout);
        setSupportActionBar(toolbar);
        progress= new ProgressDialog(this);
        progress.setMessage("Retrieve data...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        getUserData();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        setNavigationHeader();
        navigation_view.setNavigationItemSelectedListener(this);

        // this is used for clear session i.e logout from linkedin

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LISessionManager.getInstance(getApplicationContext()).clearSession();
                Intent intent = new Intent(UserProfile.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }



    public void getUserData(){
        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(UserProfile.this, topCardUrl, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse result) {
                try {

                    setUserProfile(result.getResponseDataAsJson());
                    progress.dismiss();

                } catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onApiError(LIApiError error) {

            }
        });
    }
    /*
       Set Navigation header by using Layout Inflater.
     */

    public void setNavigationHeader(){

        navigation_view = (NavigationView) findViewById(R.id.nav_view);

        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_source, null);
        navigation_view.addHeaderView(header);


    }




    public  void  setUserProfile(JSONObject response){

        try {

            user_mobile.setText(response.get("mobile").toString());
            user_name.setText(response.get("formattedName").toString());

            Picasso.with(this).load(response.getString("pictureUrl"))
                    .into(profile_pic);

        } catch (Exception e){
            e.printStackTrace();
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
}