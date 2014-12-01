package com.hans.localirk.rest;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.hans.localirk.defines.ApiDefines;
import com.hans.localirk.utils.TokenStore;
import com.hans.localirk.utils.DeviceInfo;

import org.apache.http.message.BasicNameValuePair;
import org.msgpack.MessagePack;
import org.msgpack.type.Value;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomRequest extends Request<String> {
    private List<BasicNameValuePair> params;        // the request params
    private Response.Listener<String> listener; // the response listener

    public CustomRequest(int requestMethod, String url, List<BasicNameValuePair> params,
                         Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        super(requestMethod, url, errorListener); // Call parent constructor

        this.params = params;
        this.listener = responseListener;

        Log.e("IN REQUEST", params.toString());
    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response); // Call response listener
    }

    // Proper parameter behavior
    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> map = new HashMap<String, String>();

        // Iterate through the params and add them to our HashMap
        for (BasicNameValuePair pair : params) {
            map.put(pair.getName(), pair.getValue());
        }

        return map;
    }

    // Same as JsonObjectRequest #parseNetworkResponse
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode == 204) {
            return Response.success(null, null);
        }
        Log.w("Api response headers", response.headers.toString());

        if (ApiDefines.USE_MSGPACK) {
            try {
                MessagePack messagePack = new MessagePack();
                Value dynamic = messagePack.read(response.data);
                String jsonString = dynamic.toString();

                return Response.success(jsonString, null);
            } catch (UnsupportedEncodingException exception) {
                return Response.error(new ParseError(exception));
            } catch (IOException exception) {
                return Response.error(new ParseError(exception));
            } catch (Exception exception) {
                return Response.error(new ParseError(exception));
            }
        } else {
            try {
                String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

                return Response.success(jsonString, null);
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (Exception je) {
                return Response.error(new ParseError(je));
            }
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        String userAgent = DeviceInfo.getUserAgent();
        String xDI = DeviceInfo.getXDI();
        String deviceUuid = DeviceInfo.getDeviceUUID();
        String token = TokenStore.getInstance().getToken();

        Map<String, String> params = new HashMap<String, String>();
        params.put("User-Agent", userAgent);
        params.put("X-DI", xDI);
        params.put("X-DeviceUuid", deviceUuid);
        if (token != null) {
            params.put("X-SessionID", token);
        }
        if (ApiDefines.USE_MSGPACK) {
            params.put("X-AcceptMsgpack", "Yes");
        }

        return params;
    }
}