package com.mobiledata.sg.network.CommonUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class Utility {
    public static final String TABLEDATABSE_NAME ="MobileData";
    public static final int REQUEST_CODE =1232;
    public static final String NETWORKDATA ="sum_network_data";
    public static final String MIN_min_network_data ="min_network_data";
    public static final String YEAR ="year";
    public static final String QUARTER ="quarter";


    /***
     * Check the internet connection
     */
    public static boolean isNetworkAvailable(Context ctx) {
        WifiManager wifi = (WifiManager) ctx.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (wifi.isWifiEnabled() && (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else if ((connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else {
            return false;
        }
    }
}
