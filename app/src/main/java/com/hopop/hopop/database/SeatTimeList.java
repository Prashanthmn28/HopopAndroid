package com.hopop.hopop.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class SeatTimeList extends SugarRecord implements Parcelable {

    @SerializedName("route_id")
    private String routeId;
    @SerializedName("via_route")
    private String viaRoute;
    @SerializedName("trip_id")
    private String tripId;
    @SerializedName("from_route")
    private String fromRoute;
    @SerializedName("to_route")
    private String toRoute;
    @SerializedName("time_slot")
    private String timeSlot;
    @SerializedName("seats_available")
    private String seatsAvailable;

    String fillingStatus;
    int color,imageId;


    @Override
    public String toString() {
        return "SeatTimeList{" +
                "seatsAvailable='" + seatsAvailable + ",timeSlot='"+timeSlot+ '\'' +
                '}';
    }

    public SeatTimeList(String fillingStatus, int color, int imageId) {

        this.fillingStatus = fillingStatus;

        this.color = color;
        this.imageId = imageId;

    }

    public String getFromRoute() {
        return fromRoute;
    }

    public void setFromRoute(String fromRoute) {
        this.fromRoute = fromRoute;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(String seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getToRoute() {
        return toRoute;
    }

    public void setToRoute(String toRoute) {
        this.toRoute = toRoute;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getViaRoute() {
        return viaRoute;
    }

    public void setViaRoute(String viaRoute) {
        this.viaRoute = viaRoute;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getFillingStatus() {
        return fillingStatus;
    }

    public void setFillingStatus(String fillingStatus) {
        this.fillingStatus = fillingStatus;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(routeId);
        dest.writeString(viaRoute);
        dest.writeString(tripId);
        dest.writeString(fromRoute);
        dest.writeString(toRoute);
        dest.writeString(timeSlot);
        dest.writeString(seatsAvailable);
    }
    public SeatTimeList(Parcel in) {
        routeId = in.readString();
        viaRoute = in.readString();
        tripId = in.readString();
        fromRoute = in.readString();
        toRoute = in.readString();
        timeSlot = in.readString();
        seatsAvailable = in.readString();
    }

    public static final Creator<SeatTimeList> CREATOR = new Creator<SeatTimeList>() {
        @Override
        public SeatTimeList createFromParcel(Parcel in) {
            return new SeatTimeList(in);
        }

        @Override
        public SeatTimeList[] newArray(int size) {
            return new SeatTimeList[size];
        }
    };
}
