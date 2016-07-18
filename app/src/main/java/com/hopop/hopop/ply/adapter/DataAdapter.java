package com.hopop.hopop.ply.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hopop.hopop.database.SeatTimeList;
import com.hopop.hopop.login.activity.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<SeatTimeList> seatTimeLst;
    private Context context;
    static ItemClickListenr itemClickListenr;


    public DataAdapter(ArrayList<SeatTimeList> seatTimeLst) {
        this.seatTimeLst = seatTimeLst;

    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_service_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {

        holder.tnumOfSeats.findViewById(R.id.textView_seats);
        holder.numOfSeats.setText(seatTimeLst.get(position).getSeatsAvailable());
        holder.numOfSeats.setTypeface(null,Typeface.BOLD);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String systime = sdf.format(new Date());

        String timeSlot = seatTimeLst.get(position).getTimeSlot();

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = sdf.parse(systime);
            Log.i(getClass().getSimpleName(),"system time"+d1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            d2 = sdf.parse(timeSlot);
            Log.i(getClass().getSimpleName(),"given time"+d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //in milliseconds
        long diff = d2.getTime() - d1.getTime();

        if(diff<0)
        {
            //negative values
        }
        else
        {
            Log.i(getClass().getSimpleName(),"diff time"+diff);
            long diffSeconds = diff / 1000 ;
            long diffMinutes = diffSeconds/ 60;
            Log.i(getClass().getSimpleName(),"diff in mins"+diffMinutes);

            if(diffMinutes<=59)
            {
                holder.timeSlot.setText(String.valueOf(diffMinutes)+" mins");
            }
            else
            {
                long diffHours = diffSeconds / 3600;
                Log.i(getClass().getSimpleName(), "diff in hours" + diffHours);
                long hourmins = diffMinutes - (diffHours * 60);
                Log.i(getClass().getSimpleName(), "diff in hours" + hourmins);
                holder.timeSlot.setText(String.valueOf(diffHours) + " Hours " + String.valueOf(hourmins) + " mins");

            }
            holder.timeSlot.setTypeface(null, Typeface.BOLD);
            holder.ttimeSlot.findViewById(R.id.textView_slot);

            try {
                if( Integer.parseInt(seatTimeLst.get(position).getSeatsAvailable()) <=12  && Integer.parseInt(seatTimeLst.get(position).getSeatsAvailable()) > 7){
                    holder.mimageId.setImageResource(R.drawable.orangebus);
                    holder.mFillingStatus.setText("Slowly Filling");
                    holder.mFillingStatus.setTextColor(Color.rgb(255, 145, 5));
                }else if(Integer.parseInt(seatTimeLst.get(position).getSeatsAvailable()) <=6  && Integer.parseInt(seatTimeLst.get(position).getSeatsAvailable()) > 0){
                    holder.mimageId.setImageResource(R.drawable.redbus);
                    holder.mFillingStatus.findViewById(R.id.tv_filling_status);
                    holder.mFillingStatus.setTextColor(Color.rgb(237, 28, 2));
                }else {
                    holder.mimageId.setImageResource(R.drawable.greenbus);
                    holder.mFillingStatus.setText("Booking Started");
                    holder.mFillingStatus.setTextColor(Color.rgb(0, 179, 0));
                }
            }catch (Exception e){
                Log.d(getClass().getSimpleName(),e.getMessage());
            }
        }//else of positive value
    }

    @Override
    public int getItemCount() {
        return seatTimeLst.size();
    }

    public void setOnItemClickListener(DataAdapter.ItemClickListenr itemClickListenr) {
        this.itemClickListenr = (ItemClickListenr) itemClickListenr;
    }

    public interface ItemClickListenr {
        void onItemClick(int position, View v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView numOfSeats,tnumOfSeats, timeSlot,ttimeSlot, mFillingStatus;
        public ImageView mimageId;

        public ViewHolder(View itemView) {
            super(itemView);
            tnumOfSeats = (TextView)itemView.findViewById(R.id.textView_seats);
            ttimeSlot = (TextView)itemView.findViewById(R.id.textView_slot);
            numOfSeats = (TextView) itemView.findViewById(R.id.numOfSeats);
            mFillingStatus = (TextView) itemView.findViewById(R.id.tv_filling_status);
            timeSlot = (TextView) itemView.findViewById(R.id.timeSlot);
            mimageId = (ImageView) itemView.findViewById(R.id.imageView2);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemClickListenr.onItemClick(getAdapterPosition(),v);
        }
    }
}
