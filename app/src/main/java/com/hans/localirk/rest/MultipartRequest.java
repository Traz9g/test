package com.hans.localirk.rest;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class MultipartRequest extends Request<String> {
    private MultipartEntity entity = new MultipartEntity();

    private static final String FILE_PART_NAME = "file";
    private static final String STRING_PART_NAME = "text";

    private final Response.Listener<String> mListener;
    private final Map<String, File> mFilePart;
    private final List<BasicNameValuePair> mStringPart;

    public MultipartRequest(String url, List<BasicNameValuePair> post, Map<String, File> files,
                            Response.ErrorListener errorListener, Response.Listener<String> listener) {
        super(Method.POST, url, errorListener);

        mListener = listener;
        mFilePart = files;
        mStringPart = post;
        buildMultipartEntity();
    }

    private void buildMultipartEntity() {
        for (String key : mFilePart.keySet()) {
            File f = mFilePart.get(key);
            if (f != null) {
                entity.addPart(key, new FileBody(f));
            }
        }

        for (BasicNameValuePair b : mStringPart) {
            try {
                entity.addPart(b.getName(), new StringBody(b.getValue()));
            } catch (UnsupportedEncodingException e) {
                VolleyLog.e("UnsupportedEncodingException");
            }
        }
    }

    @Override
    public String getBodyContentType() {
        return entity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        return Response.success("Uploaded", getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }
}
