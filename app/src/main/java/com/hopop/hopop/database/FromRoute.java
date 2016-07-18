package com.hopop.hopop.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.query.Condition;
import com.orm.query.Select;

@Table
public class FromRoute extends SugarRecord implements Parcelable {

    @SerializedName("stop_id")
    private String stopId;
    @SerializedName("stop_location")
    private String stopLocation;
    @SerializedName("route_id")
    private String routeId;

    @Override
    public String toString() {
        return "FromRoute{" +
                "stopLocation='" + stopLocation + ",routeId='"+routeId+ '\'' +
                '}';
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getStopLocation() {
        return stopLocation;
    }

    public void setStopLocation(String stopLocation) {
        this.stopLocation = stopLocation;
    }

    public FromRoute() {
    }

    public static boolean isNew(String stopID){
        long count = Select.from(FromRoute.class).where(Condition.prop("stop_id").eq(stopID)).count();
        if(count>0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getStopId());
        dest.writeString(this.stopLocation);
        dest.writeString(this.routeId);
    }

    protected FromRoute(Parcel in) {
        this.stopId = in.readString();
        this.stopLocation = in.readString();
        this.routeId = in.readString();
    }

    public static final Creator<FromRoute> CREATOR = new Creator<FromRoute>() {
        @Override
        public FromRoute createFromParcel(Parcel source) {
            return new FromRoute(source);
        }

        @Override
        public FromRoute[] newArray(int size) {
            return new FromRoute[size];
        }
    };
}
