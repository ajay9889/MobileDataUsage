package com.mobiledata.sg.network;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class MyMainApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
