package com.hopop.hopop.source.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hopop.hopop.database.ProfileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by girishvinu on 8/1/2016.
 */
public class HeaderProfile implements Parcelable {
    @SerializedName("profile_info")
    private List<ProfileInfo> profileInfo = new ArrayList<ProfileInfo>();

    public List<ProfileInfo> getProfileInfo() {
        return profileInfo;
    }

    public void setProfileInfo(List<ProfileInfo> profileInfo) {
        this.profileInfo = profileInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.profileInfo);
    }

    public HeaderProfile() {
    }

    protected HeaderProfile(Parcel in) {
        this.profileInfo = in.createTypedArrayList(ProfileInfo.CREATOR);
    }

    public static final Creator<HeaderProfile> CREATOR = new Creator<HeaderProfile>() {
        @Override
        public HeaderProfile createFromParcel(Parcel source) {
            return new HeaderProfile(source);
        }
        @Override
        public HeaderProfile[] newArray(int size) {
            return new HeaderProfile[size];
        }
    };
}

