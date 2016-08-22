package com.hopop.hopop.sidenavigation.feedback.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.facebook.stetho.json.annotation.JsonProperty;


public class FeedbackInfo  {

    public String success;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

}
