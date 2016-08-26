package com.hopop.hopop.communicators.prefmanager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.hopop.hopop.infrastructure.activity.HopopApplication;
import com.hopop.hopop.registration.activity.MyApplication;

public class PrefManager {

    public static String AUTH_KEY = "auth_key";


    public static SharedPreferences getPreferences(){
        Log.d("PrefManager","the instance is "+MyApplication.getInstance());
        return PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
    }

    public static void setString(String preferenceKey,String preferenceValue){
        getPreferences().edit().putString(preferenceKey,preferenceValue).commit();
    }

    public static String getString(String preferenceKey,String preferenceDefaultValue){
        return getPreferences().getString(preferenceKey,preferenceDefaultValue);
    }

    public static void putAuthKey(String authKey){
        setString(AUTH_KEY,authKey);
    }

    public static String getAuehKey(){
        return getString(AUTH_KEY,"NA");
    }

    public static String getToken() {
        String token;
        return getToken();
    }
}
