package com.hopop.hopop.source.adapter;

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

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SrcRecyclerAdapter extends RecyclerView.Adapter<SrcRecyclerAdapter.DataObjectHolder> implements Filterable {
    public static List<FromRoute> fromRoutes;
    private Context context;
    static ItemClickListenr itemClickListenr;
    public LinkedBlockingDeque filteredList;
    SearchFilter searchFilter;

    public SrcRecyclerAdapter(List<FromRoute> fromRoutes, Context context){
        this.fromRoutes = fromRoutes;
        this.context = context;
        searchFilter = new SearchFilter(this, this.fromRoutes);
    }

    @Override
    public int getItemCount() {
        return fromRoutes.size();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.route_item, viewGroup, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder dataObjectHolder, int position) {
        dataObjectHolder.tv_entity_name.setText(fromRoutes.get(position).getStopLocation());
    }

    public FromRoute getFilteredItem(int position) {
        List<FromRoute> filteredList = searchFilter.getFilteredList();
        if (filteredList == null || filteredList.isEmpty()) {
            return fromRoutes.get(position);
        }
        return filteredList.get(position);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener{
        private static final String TAG = "Source OnClick";
        @Bind(R.id.tv_entity_name)
        TextView tv_entity_name;
        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Log.i(getClass().getSimpleName(), "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick " + getPosition() + " " + fromRoutes);
            if (itemClickListenr != null)  itemClickListenr.onItemClick(getAdapterPosition(),view);
        }
    }

    public void setOnItemClickListener(ItemClickListenr itemClickListenr) {
        this.itemClickListenr = itemClickListenr;
    }
    public interface ItemClickListenr {
        void onItemClick(int position, View v);
    }
}