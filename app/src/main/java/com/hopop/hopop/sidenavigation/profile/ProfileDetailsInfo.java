package com.hopop.hopop.sidenavigation.profile;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProfileDetailsInfo implements Parcelable {

    @SerializedName("profile_details")
    private List<ProfileDetail> profileDetails = new ArrayList<ProfileDetail>();

    public List<ProfileDetail> getProfileDetails() {
        return profileDetails;
    }

    public void setProfileDetails(List<ProfileDetail> profileDetails) {
        this.profileDetails = profileDetails;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.profileDetails);
    }

    public ProfileDetailsInfo() {
    }

    protected ProfileDetailsInfo(Parcel in) {
        this.profileDetails = in.createTypedArrayList(ProfileDetail.CREATOR);
    }

    public static final Creator<ProfileDetailsInfo> CREATOR = new Creator<ProfileDetailsInfo>() {
        @Override
        public ProfileDetailsInfo createFromParcel(Parcel source) {
            return new ProfileDetailsInfo(source);
        }

        @Override
        public ProfileDetailsInfo[] newArray(int size) {
            return new ProfileDetailsInfo[size];
        }
    };
}
