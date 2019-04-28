package com.tan.lib_utils_android.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class NetWorkUtil {

    public static int NET_NONE = 0;
    public static int NET_WIFI = 1;
    public static int NET_MOBILE = 2;

    public static int getNetWorkState(Context context) {

        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // Wifi
       State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
            return NET_WIFI;
        }

        // MOBILE
        state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
            return NET_MOBILE;
        }
        return NET_NONE;
    }
}
