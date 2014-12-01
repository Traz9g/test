package com.hans.localirk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hans.localirk.LikrApp;


public class TokenStore {
    private static Context context = LikrApp.getContext();

    private static String _token;

    private TokenStore() {
        _token = TokenPreferences.getTokenFromPreferences();
        Log.d("TokenStore", "new instance");
    }

    public static TokenStore getInstance() {
        return TokenStoreHolder.HOLDER_INSTANCE;
    }

    public String getToken() {
        return _token;
    }

    public void setToken(String token) {
        _token = token;
        TokenPreferences.storeToken();

        Log.i("TokenStore", "new token:\n" + token);
    }

    public static class TokenStoreHolder {
        public static final TokenStore HOLDER_INSTANCE = new TokenStore();
    }

    private static class TokenPreferences {
        private static final String PREFERENCE_NAME = LikrApp.getAppName();
        private static final String PREFERENCE_TOKEN = "token";

        private static void storeToken() {
            SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            preferences.edit().putString(PREFERENCE_TOKEN, _token).apply();
        }

        private static String getTokenFromPreferences() {
            SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            return preferences.getString(PREFERENCE_TOKEN, null);
        }
    }
}