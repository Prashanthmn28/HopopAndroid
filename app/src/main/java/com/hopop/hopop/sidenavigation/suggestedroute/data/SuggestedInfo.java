package com.hopop.hopop.sidenavigation.suggestedroute.data;

import com.facebook.stetho.json.annotation.JsonProperty;

public class SuggestedInfo {

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @JsonProperty
    private String success;
}
