package com.hopop.hopop.ply.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.ply.service.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends BaseAdapter {

    private List<Service> data = new ArrayList();
    private Context context;
    private LayoutInflater inflater = null;


    public ServiceAdapter(Context context, List<Service> data) {
        this.context = context;
        this.data = data;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Service getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        View vi = rowView;
        ViewHolder holder;


        if (rowView == null) {
            vi = inflater.inflate(R.layout.list_service_item, null);
            holder = new ViewHolder();
            holder.mInterval = (TextView) vi.findViewById(R.id.tv_interval);
            holder.mAvailability = (TextView) vi.findViewById(R.id.tv_availability);
            holder.mFillingStatus = (TextView) vi.findViewById(R.id.tv_filling_status);
            holder.mimageId = (ImageView)vi.findViewById(R.id.imageView2);



            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        Service service = getItem(position);
        if (service != null) {
            holder.mInterval.setText(Html.fromHtml(service.getInterval()));
            holder.mFillingStatus.setText(service.getFillingStatus());
            holder.mFillingStatus.setTextColor(context.getResources().getColor(service.getColor()));
            holder.mAvailability.setText(service.getAvailability());
            holder.mimageId.setImageResource(service.getimageId());

        }

        return vi;
    }

    public static class ViewHolder {
        public TextView mInterval, mAvailability, mFillingStatus;
        public ImageView mimageId;
    }
}
