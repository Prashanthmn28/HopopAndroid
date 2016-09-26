package com.hopop.hopop.destination.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.hopop.hopop.database.FromRoute;
import com.hopop.hopop.login.activity.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DestRecyclerAdapter extends RecyclerView.Adapter<DestRecyclerAdapter.ViewHolder> implements Filterable {

    private List<FromRoute> stopPoints;
    private Context context;
    static ItemClickListenr itemClickListenr;
    public ArrayList<FromRoute> filteredList;

    DestSearchFilter searchFilter;

    public DestRecyclerAdapter(List<FromRoute> stopPoints, Context destinationActivity) {
        this.stopPoints = stopPoints;
        this.context = destinationActivity;
        searchFilter = new DestSearchFilter(this, stopPoints);
        filteredList = (ArrayList<FromRoute>) stopPoints;
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }


    @Override
    public DestRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.route_item, parent, false);

        ViewHolder dataObjectHolder = new ViewHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DestRecyclerAdapter.ViewHolder holder, int position) {
        holder.tv_entity_name.setText(filteredList.get(position).getStopLocation());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void setOnItemClickListener(ItemClickListenr dest) {
        this.itemClickListenr = dest;
    }

    public FromRoute getFilteredItem(int position) {
        List<FromRoute> filteredList = searchFilter.getFilteredList();
        if (filteredList == null || filteredList.isEmpty()) {
            return stopPoints.get(position);
        }
        return filteredList.get(position);
    }

    public interface ItemClickListenr {
        void onItemClick(int adapterPosition, View v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        @Bind(R.id.tv_entity_name)
        TextView tv_entity_name;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListenr != null) itemClickListenr.onItemClick(getAdapterPosition(),v);
        }

    }
}
