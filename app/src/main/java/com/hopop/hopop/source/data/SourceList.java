package com.hopop.hopop.source.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hopop.hopop.database.FromRoute;

import java.util.ArrayList;
import java.util.List;

public class SourceList implements Parcelable {

    @SerializedName("auth_key")
    private String auth_key;

    @SerializedName("From_Routes")
    private ArrayList<FromRoute> fromRoutes = new ArrayList<FromRoute>();
    private Integer success;

    @Override
    public String toString() {
        return "SourceList{" +
                "fromRoutes=" + fromRoutes +
                ", success=" + success +
                '}';
    }

    public String getAuth_key() {
        return auth_key;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }

    public List<FromRoute> getFromRoutes() {
        return fromRoutes;
    }

    public void setFromRoutes(ArrayList<FromRoute> fromRoutes) {
        this.fromRoutes = fromRoutes;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public SourceList() {
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.fromRoutes);
        dest.writeValue(this.success);
        dest.writeValue(this.auth_key);
    }

    protected SourceList(Parcel in) {
        this.fromRoutes = in.createTypedArrayList(FromRoute.CREATOR);
        this.success = (Integer) in.readValue(Integer.class.getClassLoader());
        this.auth_key = (String) in.readValue(String.class.getClassLoader());
    }

    public static final Creator<SourceList> CREATOR = new Creator<SourceList>() {
        @Override
        public SourceList createFromParcel(Parcel source) {
            return new SourceList(source);
        }
        @Override
        public SourceList[] newArray(int size) {
            return new SourceList[size];
        }
    };

}