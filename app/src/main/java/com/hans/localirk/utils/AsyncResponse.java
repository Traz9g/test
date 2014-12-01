package com.hans.localirk.utils;

import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.hans.localirk.R;
import com.hans.localirk.defines.ApiDefines;
import com.hans.localirk.dialogs.DialogServerResponseError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AsyncResponse {
    private static final String TAG = AsyncResponse.class.getSimpleName();
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    private String response;

    public AsyncResponse(String response) {
        this.response = response;
    }

    public boolean ifSuccess() {
        boolean success = false;

        try {
            if (response != null) {
                jsonObject = new JSONObject(response);

                success = !jsonObject.has(ApiDefines.ERROR);
            }

            return success;
        } catch (JSONException exception) {
            Log.e(TAG, "Error parsing data " + exception.toString());

            return false;
        }
    }

    public void saveToken() {
        try {
            jsonObject = new JSONObject(response);

            if (jsonObject.has(ApiDefines.SESSION_ID)) {
                TokenStore store = TokenStore.getInstance();
                store.setToken(jsonObject.getString(ApiDefines.SESSION_ID));
            }
        } catch (JSONException exception) {
            Log.e(TAG, "Error parsing data " + exception.toString());
        }
    }

    public void clearToken() {
        TokenStore.getInstance().setToken("");
    }

    public void showAlert(FragmentManager fragmentManager) {
        if (response != null) {
            if (response.contains(ApiDefines.ERROR)) {

                int dialogTitle = R.string.responseErrorTitle;
                int dialogMessage = R.string.clientTooOld;

                /*if (jsonObject.has(ApiDefines.CLIENT_TOO_OLD)) {
                    dialogMessage = activity.getString(R.string.clientTooOld);
                } else if (jsonObject.has(ApiDefines.CHAT_ACTION_UNAUTHORIZED)) {
                    dialogMessage = activity.getString(R.string.chatActionUnauthorized);
                } else {
                    dialogMessage = "";
                }*/

                DialogServerResponseError.newInstance(dialogTitle, dialogMessage)
                        .show(fragmentManager, ApiDefines.ERROR);
            }
        }
    }

    public int getCount() {
        return jsonArray.length();
    }
}
