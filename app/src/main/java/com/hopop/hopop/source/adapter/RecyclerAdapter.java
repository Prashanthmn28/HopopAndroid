package com.hopop.hopop.source.adapter;

import android.content.Context;
import android.graphics.Color;
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
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.DataObjectHolder> implements Filterable {
    private List<FromRoute> fromRoutes;
    private Context context;
    static ItemClickListenr itemClickListenr;
    public LinkedBlockingDeque filteredList;


    public RecyclerAdapter(List<FromRoute> fromRoutes,Context context){
        this.fromRoutes = fromRoutes;
        this.context = context;
    }

    @Override
    public Filter getFilter() {
        return new SearchFilter(this, fromRoutes);
    }

    private static  class SearchFilter extends Filter {

        private final RecyclerAdapter adapter;
        private final List<FromRoute> originalList;

        final List<FromRoute> filteredList;


        public SearchFilter(RecyclerAdapter recyclerAdapter, List<FromRoute> fromRoutes) {

            super();
            this.adapter = recyclerAdapter;
            this.originalList = new LinkedList<>(fromRoutes);
            this.filteredList = new ArrayList<>();

        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final FromRoute src : originalList) {
                    if (src.getStopLocation().contains(filterPattern)) {
                        filteredList.add(src);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filteredList.clear();
            adapter.filteredList.addAll((ArrayList<FromRoute>) results.values);
            adapter.notifyDataSetChanged();
        }
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

    @Override
    public int getItemCount() {
        return fromRoutes.size();
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener{
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
            itemClickListenr.onItemClick(getAdapterPosition(),view);
        }
    }

    public void setOnItemClickListener(ItemClickListenr itemClickListenr) {
        this.itemClickListenr = itemClickListenr;
    }
    public interface ItemClickListenr {
        void onItemClick(int position, View v);
    }
}