package com.hopop.hopop.sidenavigation.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.sidenavigation.profile.adapter.ProfilePicAdapter;
import com.hopop.hopop.source.activity.SourceActivity;

public class GridImgActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepic);

        GridView gridView = (GridView)findViewById(R.id.gridview_profilepic);
        gridView.setAdapter(new ProfilePicAdapter(getApplicationContext()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Sending image id to FullScreenActivity
                Intent i = new Intent(getApplicationContext(), SourceActivity.class);
                // passing array index
                i.putExtra("id", position);
                startActivity(i);
            }
        });
    }
}
