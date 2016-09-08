package com.hopop.hopop.sidenavigation.profile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.hopop.hopop.login.activity.R;

public class ProfilePicAdapter extends BaseAdapter {

    private Context mContex;

    public Integer[] picArry ={
            R.drawable.men1,R.drawable.men2,R.drawable.men3,R.drawable.men4,R.drawable.men5,
            R.drawable.women1,R.drawable.women2,R.drawable.women2,R.drawable.women3,R.drawable.women4,
            R.drawable.women5

    };

    public ProfilePicAdapter(Context context) {
        this.mContex = context;
    }



    @Override
    public int getCount() {
        return picArry.length;
    }



    @Override
    public Object getItem(int position) {
        return picArry[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView iv = new ImageView(mContex);
        iv.setImageResource(picArry[position]);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setLayoutParams(new GridView.LayoutParams(70,70));
        return iv;
    }
}
