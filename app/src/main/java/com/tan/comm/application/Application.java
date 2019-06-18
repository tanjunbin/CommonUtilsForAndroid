package com.tan.comm.application;

import android.content.Context;

public class Application extends android.support.multidex.MultiDexApplication {

    private static Context mContext;

    public static Context getApplication() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
    }
}
