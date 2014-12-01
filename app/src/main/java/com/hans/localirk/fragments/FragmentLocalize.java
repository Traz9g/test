package com.hans.localirk.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hans.localirk.R;
import com.hans.localirk.interfaces.OnAsyncTaskListener;
import com.hans.localirk.rest.RestClient;

public class FragmentLocalize extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_localize, null);

        RestClient.postUserLocation("60.468113,22.30749", "1", null, new OnAsyncTaskListener() {
            @Override
            public void onTaskBegin() {
            }

            @Override
            public void onTaskComplete(boolean isComplete, String message) {

            }
        });

        return rootView;
    }
}
