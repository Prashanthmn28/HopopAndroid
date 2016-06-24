package com.hopop.hopop.ply.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hopop.hopop.database.SeatTimeList;

import java.util.ArrayList;
import java.util.List;

public class SeatTimeInfo implements Parcelable {

    @SerializedName("seat_time_list")
    private ArrayList<SeatTimeList> seatTimeList = new ArrayList<SeatTimeList>();


    @Override
    public String toString() {
        return "SeatTimeInfo{" +
                "seatTimeList=" + seatTimeList +

                '}';
    }

    public SeatTimeInfo() {

    }

    public ArrayList<SeatTimeList> getSeatTimeList() {
        return seatTimeList;
    }

    public void setSeatTimeList(ArrayList<SeatTimeList> seatTimeList) {
        this.seatTimeList = seatTimeList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.seatTimeList);
    }

    protected SeatTimeInfo(Parcel in) {
        this.seatTimeList = in.createTypedArrayList(SeatTimeList.CREATOR);
    }

    public static final Creator<SeatTimeInfo> CREATOR = new Creator<SeatTimeInfo>() {
        @Override
        public SeatTimeInfo createFromParcel(Parcel source) {
            return new SeatTimeInfo(source);
        }

        @Override
        public SeatTimeInfo[] newArray(int size) {
            return new SeatTimeInfo[size];
        }
    };
}
