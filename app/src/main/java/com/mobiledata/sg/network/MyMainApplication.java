package com.mobiledata.sg.network;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.mobiledata.sg.network.CommonUtils.Utility;
import com.mobiledata.sg.network.RoomPersistDatabase.Databasehelper;

public class MyMainApplication extends MultiDexApplication {
    public static Databasehelper INSTANCE;
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE=  Databasehelper.getDatabase(this, Utility.TABLEDATABSE_NAME);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
