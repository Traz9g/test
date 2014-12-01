package com.hans.localirk.rest;

import com.hans.localirk.defines.ApiDefines;
import com.hans.localirk.interfaces.OnAsyncTaskListener;
import com.hans.localirk.utils.AsyncLoadVolley;

import java.util.HashMap;
import java.util.Map;

public class RestClient {
    private static AsyncLoadVolley asyncLoader = new AsyncLoadVolley();

    public static void login(String userName, String password,
                             OnAsyncTaskListener listener) {
        String path = ApiDefines.PATH_LOGIN;

        Map<String, String> map = new HashMap<String, String>();
        map.put(ApiDefines.USERNAME, userName);
        map.put(ApiDefines.PASSWORD, password);

        asyncLoader.addDirectRequest(path, map, listener);
    }

    public static void logout(OnAsyncTaskListener listener) {
        String path = ApiDefines.PATH_LOGOUT;

        Map<String, String> map = new HashMap<String, String>();

        asyncLoader.addDirectRequest(path, map, listener);
    }

    public static void loginSession(OnAsyncTaskListener listener) {
        String path = ApiDefines.PATH_LOGIN_SESSION;

        Map<String, String> map = new HashMap<String, String>();

        asyncLoader.addDirectRequest(path, map, listener);
    }

    public static void enterAsGuest(String userName, String age, String ageGroup, String gender,
                                    OnAsyncTaskListener listener) {
        String path = ApiDefines.PATH_ENTER_AS_GUEST;

        Map<String, String> map = new HashMap<String, String>();
        map.put(ApiDefines.USERNAME, userName);
        map.put(ApiDefines.AGE, age);
        map.put(ApiDefines.AGE_GROUP, ageGroup);
        map.put(ApiDefines.GENDER, gender);

        asyncLoader.addDirectRequest(path, map, listener);
    }

    public static void singUp(String name, String password, String email, String age,
                              String ageGroup, String gender, String imageID, OnAsyncTaskListener listener) {
        String path = ApiDefines.PATH_SING_UP;

        Map<String, String> map = new HashMap<String, String>();
        map.put(ApiDefines.USERNAME, name);
        map.put(ApiDefines.PASSWORD, password);
        map.put(ApiDefines.EMAIL, email);
        map.put(ApiDefines.IMAGE_ID, imageID);
        map.put(ApiDefines.AGE, age);
        map.put(ApiDefines.AGE_GROUP, ageGroup);
        map.put(ApiDefines.GENDER, gender);

        asyncLoader.addDirectRequest(path, map, listener);
    }

    public static void postUserLocation(String location, String fetchPlaces, String reason, OnAsyncTaskListener listener) {
        String path = ApiDefines.PATH_LOCATION;

        Map<String, String> map = new HashMap<String, String>();
        map.put(ApiDefines.LOCATION, location);
        map.put(ApiDefines.FETCH_PLACES, fetchPlaces);
        if (reason != null) {
            map.put(ApiDefines.REASON, reason);
        }

        asyncLoader.addDirectRequest(path, map, listener);
    }
}