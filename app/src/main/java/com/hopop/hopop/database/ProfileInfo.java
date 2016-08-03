package com.hopop.hopop.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by girishvinu on 8/1/2016.
 */
public class ProfileInfo implements Parcelable {

    @SerializedName("mobile_number")
    public String mobile_number;
    @SerializedName("user_name")
    public String user_name;

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mobile_number);
        dest.writeString(this.user_name);
    }

    public ProfileInfo() {
    }

    protected ProfileInfo(Parcel in) {
        this.mobile_number = in.readString();
        this.user_name = in.readString();
    }

    public static final Creator<ProfileInfo> CREATOR = new Creator<ProfileInfo>() {
        @Override
        public ProfileInfo createFromParcel(Parcel source) {
            return new ProfileInfo(source);
        }

        @Override
        public ProfileInfo[] newArray(int size) {
            return new ProfileInfo[size];
        }
    };
}
