package com.hans.localirk;

import android.app.Application;
import android.content.Context;

public class LikrApp extends Application {
    private static Context context;
    private static String appName;

    @Override public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        appName = getString(R.string.app_name);
    }

    public static Context getContext() {
        return context;
    }

    public static String getAppName() {
        return appName;
    }
}
