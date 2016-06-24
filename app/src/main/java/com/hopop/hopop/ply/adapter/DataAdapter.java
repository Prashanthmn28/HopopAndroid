package com.hopop.hopop.ply.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hopop.hopop.database.SeatTimeList;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.source.adapter.RecyclerAdapter;

import java.util.ArrayList;

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
        holder.timeSlot.setText(seatTimeLst.get(position).getTimeSlot());
        holder.ttimeSlot.findViewById(R.id.textView_slot);
        try {
            if( Integer.parseInt(seatTimeLst.get(position).getSeatsAvailable()) > 6){
                holder.mimageId.setImageResource(R.drawable.orangebus);
                holder.mFillingStatus.setText("Slowly Filling");
                holder.mFillingStatus.setTextColor(context.getResources().getColor(seatTimeLst.get(position).getColor()));
            }else if(Integer.parseInt(seatTimeLst.get(position).getSeatsAvailable()) < 6  && Integer.parseInt(seatTimeLst.get(position).getSeatsAvailable()) > 0){
                holder.mimageId.setImageResource(R.drawable.redbus);
                holder.mFillingStatus.findViewById(R.id.tv_filling_status);
                holder.mFillingStatus.setTextColor(context.getResources().getColor(seatTimeLst.get(position).getColor()));
            }else {
                holder.mimageId.setImageResource(R.drawable.greenbus);
                holder.mFillingStatus.setText("Booking Started");
                holder.mFillingStatus.setTextColor(context.getResources().getColor(seatTimeLst.get(position).getColor()));
            }
        }catch (Exception e){
            Log.d(getClass().getSimpleName(),e.getMessage());
        }



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
