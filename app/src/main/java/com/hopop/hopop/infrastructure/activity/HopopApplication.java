package com.hopop.hopop.infrastructure.activity;

import android.content.Context;

import com.hopop.hopop.communicators.services.RegisterClass;
import com.orm.SugarApp;

public class HopopApplication extends SugarApp {

    private static Context instance;
    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = super.getApplicationContext();
    }

    public static Context getInstance(){
        return instance;
    }
}
