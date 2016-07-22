package com.hopop.hopop.sidenavigation.mybooking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.database.BookingHistory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by girishvinu on 7/18/2016.
 */
public class PastRecyclerAdapter extends RecyclerView.Adapter<PastRecyclerAdapter.PastObjectHolder> implements Filterable{
    public static List<BookingHistory> bookingHis;
    private Context context;
    static ItemClickListenr itemClickListenr;
    public LinkedBlockingDeque filteredList;
    public PastRecyclerAdapter(final List<BookingHistory> past_list, Context applicationContext) {
        this.bookingHis =  past_list;
        this.context = applicationContext;
    }

    @Override
    public PastRecyclerAdapter.PastObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_past, parent, false);

        PastObjectHolder pastObj = new PastObjectHolder(view);
        return pastObj;


    }

    @Override
    public void onBindViewHolder(PastRecyclerAdapter.PastObjectHolder holder, int position) {

        String date = bookingHis.get(position).getCreatedOn();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(new Date());





        holder.getTextDate().setText(date);
        holder.getTextSrc().setText(bookingHis.get(position).getFromLocation());
        holder.getTextDst().setText(bookingHis.get(position).getToLocation());


    }

    @Override
    public int getItemCount() {
        return bookingHis.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class PastObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.text1)
        TextView textDate;
        @Bind(R.id.text2)
        TextView textSrc;
        @Bind(R.id.text3)
        TextView textDst;
        @Bind(R.id.button_rebook)
        Button reebook;
        public TextView getTextSrc() {
            return textSrc;
        }
        public void setTextSrc(TextView textSrc) {
            this.textSrc = textSrc;
        }
        public TextView getTextDst() {
            return textDst;
        }
        public void setTextDst(TextView textDst) {
            this.textDst = textDst;
        }
        public TextView getTextDate() {
            return textDate;
        }
        public void setTextDate(TextView textDate) {
            this.textDate = textDate;
        }
        public PastObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListenr != null)  itemClickListenr.onItemClick(getAdapterPosition(),v);


        }


    }

    public void setOnItemClickListener(ItemClickListenr itemClickListenr) {
        this.itemClickListenr = itemClickListenr;
    }
    public interface ItemClickListenr {
        void onItemClick(int position, View v);
    }
}
