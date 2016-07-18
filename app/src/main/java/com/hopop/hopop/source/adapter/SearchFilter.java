package com.hopop.hopop.source.adapter;

import android.widget.Filter;

import com.hopop.hopop.database.FromRoute;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public  class SearchFilter extends Filter {

    private final SrcRecyclerAdapter adapter;
    private final List<FromRoute> originalList;

    final List<FromRoute> filteredList;


    public SearchFilter(SrcRecyclerAdapter recyclerAdapter, List<FromRoute> fromRoutes) {

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

    public List<FromRoute> getFilteredList() {
        return filteredList;
    }
}
