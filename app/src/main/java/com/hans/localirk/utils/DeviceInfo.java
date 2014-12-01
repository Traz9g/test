package com.hans.localirk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.hans.localirk.LikrApp;

import java.util.UUID;

public class DeviceInfo {
    public static String getUserAgent() {
        //return System.getProperty("http.agent");
        return "fakebot";
    }

    public static String getXDI() {
        String systemName = "Android";
        String systemVersion = String.valueOf(Build.VERSION.RELEASE);
        String deviceModel = getDeviceName();
        String networkType = getNetworkClass();

        return systemName + "," + systemVersion + "," + deviceModel + "," + networkType;
    }

    public static String getDeviceUUID() {
        String PREF_UNIQUE_ID = getDeviceName();
        String uniqueID;

        SharedPreferences sharedPrefs = LikrApp.getContext()
                .getSharedPreferences(PREF_UNIQUE_ID, Context.MODE_PRIVATE);
        uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);

        if (uniqueID == null) {
            uniqueID = UUID.randomUUID().toString();

            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(PREF_UNIQUE_ID, uniqueID);
            editor.apply();
        }

        return uniqueID;
    }

    private static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private static String capitalize(String line) {
        if (line == null || line.length() == 0) {
            return "";
        }

        char first = line.charAt(0);

        if (Character.isUpperCase(first)) {
            return line;
        } else {
            return Character.toUpperCase(first) + line.substring(1);
        }
    }

    private static String getNetworkClass() {
        ConnectivityManager cm = (ConnectivityManager) LikrApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if (info == null || !info.isConnected())
            return ""; //not connected

        if (info.getType() == ConnectivityManager.TYPE_WIFI)
            return info.getTypeName();
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            return info.getSubtypeName();
        }
        return "";
    }
}


