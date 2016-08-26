package com.hopop.hopop.registration.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.orm.SugarApp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyApplication extends SugarApp {
    private static Context instance;
    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = super.getApplicationContext();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
    public static Context getInstance(){
        return instance;
    }

    public void printHashKey(){

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.hopop.hopop.login.activity",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("HopOp", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {
    }

    }
}
