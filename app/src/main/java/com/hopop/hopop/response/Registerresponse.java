package com.hopop.hopop.response;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Registerresponse {
    @SerializedName("auth_key")
    private String auth_key;
    private String success;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getAuth_key() {
        return auth_key;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}