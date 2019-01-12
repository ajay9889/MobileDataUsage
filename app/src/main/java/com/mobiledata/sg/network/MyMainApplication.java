package com.mobiledata.sg.network;
import android.content.Context;
import com.mobiledata.sg.network.CommonUtils.Utility;
import com.mobiledata.sg.network.RoomPersistDatabase.Databasehelper;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

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
