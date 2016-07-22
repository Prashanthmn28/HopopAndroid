package com.hopop.hopop.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class ProfileDetail extends SugarRecord implements Parcelable {


    @SerializedName("mobile_number")
    private String mobileNumber;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("mail_id")
    private String mailId;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mobileNumber);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.mailId);
    }

    public ProfileDetail() {
    }

    protected ProfileDetail(Parcel in) {
        this.mobileNumber = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.mailId = in.readString();
    }

    public static final Creator<ProfileDetail> CREATOR = new Creator<ProfileDetail>() {
        @Override
        public ProfileDetail createFromParcel(Parcel source) {
            return new ProfileDetail(source);
        }

        @Override
        public ProfileDetail[] newArray(int size) {
            return new ProfileDetail[size];
        }
    };
}
