package com.hans.localirk.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.hans.localirk.LikrApp;
import com.hans.localirk.defines.ApiDefines;
import com.hans.localirk.interfaces.OnAsyncTaskListener;
import com.hans.localirk.rest.CustomRequest;
import com.hans.localirk.rest.MultipartRequest;

import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AsyncLoadVolley {
    private Context context = LikrApp.getContext();

    private static final String TAG = "AsyncLoadVolley Response";
    private RequestQueue queue;

    public static final int CHECK_INTERNET_CONNECTED = 101;

    private void init() {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
    }

    public void addRequest(String relativePath, Map<String, String> map, OnAsyncTaskListener asyncListener) {
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        init();

        String url = ApiDefines.URL + relativePath;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            Log.i(TAG, "key : " + key + ", val : " + value);

            params.add(new BasicNameValuePair(key, value));
        }
        asyncListener.onTaskBegin();
        RequestListener listener = new RequestListener(asyncListener);
        CustomRequest request = new CustomRequest(Request.Method.POST, url, params, listener, listener);
        queue.add(request);
    }

    public void addDirectRequest(String relativePath, Map<String, String> map, OnAsyncTaskListener asyncListener) {
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        init();
        String url = ApiDefines.URL + relativePath;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.i(TAG, "key : " + key + ", val : " + value);

            params.add(new BasicNameValuePair(key, value));
        }

        asyncListener.onTaskBegin();

        RequestListener listener = new RequestListener(asyncListener);
        CustomRequest request = new CustomRequest(Request.Method.POST, url, params, listener, listener);

        queue.add(request);
    }

    public void addGetRequest(String relativePath, OnAsyncTaskListener asyncListener) {
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        init();
        String url = ApiDefines.URL + relativePath + ".php";
        asyncListener.onTaskBegin();
        RequestListener listener = new RequestListener(asyncListener);
        CustomRequest request = new CustomRequest(Request.Method.GET, url, params, listener, listener);
        queue.add(request);
    }

    public void addDirectGetRequest(String relativePath, OnAsyncTaskListener onAsyncTaskListener) {
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        init();
        String url = ApiDefines.URL + relativePath;

        onAsyncTaskListener.onTaskBegin();
        RequestListener listener = new RequestListener(onAsyncTaskListener);
        CustomRequest request = new CustomRequest(Request.Method.GET, url, params, listener, listener);
        queue.add(request);
    }

    public void addDirectFileRequest(String relativePath, List<BasicNameValuePair> values, final File f, OnAsyncTaskListener listener) {
        init();
        String url = ApiDefines.URL + relativePath;

        listener.onTaskBegin();
        RequestListener requestListener = new RequestListener(listener);
        MultipartRequest request = new MultipartRequest(url, values,
                new HashMap<String, File>() {
                    {
                        put("userfile", f);
                    }
                }, requestListener, requestListener);
        queue.add(request);
    }

    private class RequestListener implements Response.Listener<String>, Response.ErrorListener {
        private OnAsyncTaskListener listener;

        public RequestListener(OnAsyncTaskListener listener) {
            this.listener = listener;
        }

        @Override
        public void onResponse(String response) {
            if (response != null) {
                Log.i(TAG, response);
                listener.onTaskComplete(true, response);
            } else {
                listener.onTaskComplete(true, null);
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            String errorResponse = "";

            if (error.networkResponse != null) {
                try {
                    errorResponse = new String(error.networkResponse.data, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                errorResponse = error.getMessage();
            }

            Log.e(TAG, "onErrorResponse : " + errorResponse);

            if (ConnectionDetector.isConnectedToInternet(context)) {
                listener.onTaskComplete(false, errorResponse);
            } else {
                listener.onTaskComplete(false, "Not Connected to the internet");
            }
        }
    }
}