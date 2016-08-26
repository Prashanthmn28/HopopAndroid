package com.hopop.hopop.infrastructure.activity;


import android.content.Context;

import com.orm.SugarApp;

public class HopopApplication extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = super.getApplicationContext();
    }

    public static Context getInstance(){
    private static HopopApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
    }

    public static HopopApplication getInstance(){

        return instance;
    }
}
