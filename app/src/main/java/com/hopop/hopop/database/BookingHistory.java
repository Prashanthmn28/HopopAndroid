package com.hopop.hopop.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

/**
 * Created by girishvinu on 7/15/2016.
 */
public class BookingHistory extends SugarRecord implements Parcelable {

    @SerializedName("mobile_number")
    private  String mobileNumber;
    @SerializedName("from_location")
    private String fromLocation;
    @SerializedName("to_location")
    private String toLocation;

    @SerializedName("route")
    private String route;
    @SerializedName("trip_id")
    private  String tripId;
    @SerializedName("created_on")
    private String createdOn;


    @Override
    public String toString() {
        return "BookingHistory{" +
                "fromLocation='" + fromLocation + ",toLocation='"+toLocation+ '\'' +
                ",createdOn='"+createdOn+ '\'' +'}';
    }



    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mobileNumber);
        dest.writeString(this.fromLocation);
        dest.writeString(this.toLocation);
        dest.writeString(this.route);
        dest.writeString(this.tripId);
        dest.writeString(this.createdOn);
    }

    public BookingHistory() {
    }

    public static boolean isNew(String mobileNumber){
        long count = Select.from(BookingHistory.class).where(Condition.prop("mobile_number").eq(mobileNumber)).count();
        if(count>0){
            return false;
        }else {
            return true;
        }
    }


    protected BookingHistory(Parcel in) {
        this.mobileNumber = in.readString();
        this.fromLocation = in.readString();
        this.toLocation = in.readString();
        this.route = in.readString();
        this.tripId = in.readString();
        this.createdOn = in.readString();
    }

    public static final Creator<BookingHistory> CREATOR = new Creator<BookingHistory>() {
        @Override
        public BookingHistory createFromParcel(Parcel source) {
            return new BookingHistory(source);
        }

        @Override
        public BookingHistory[] newArray(int size) {
            return new BookingHistory[size];
        }
    };
}
