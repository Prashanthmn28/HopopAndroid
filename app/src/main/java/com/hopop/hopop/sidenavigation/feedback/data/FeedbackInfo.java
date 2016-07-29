package com.hopop.hopop.sidenavigation.feedback.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.facebook.stetho.json.annotation.JsonProperty;


public class FeedbackInfo implements Parcelable  {

    @JsonProperty
    private String success;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.success);
    }

    public FeedbackInfo() {
    }

    protected FeedbackInfo(Parcel in) {
        this.success = in.readString();
    }

    public static final Creator<FeedbackInfo> CREATOR = new Creator<FeedbackInfo>() {
        @Override
        public FeedbackInfo createFromParcel(Parcel source) {
            return new FeedbackInfo(source);
        }

        @Override
        public FeedbackInfo[] newArray(int size) {
            return new FeedbackInfo[size];
        }
    };
}
