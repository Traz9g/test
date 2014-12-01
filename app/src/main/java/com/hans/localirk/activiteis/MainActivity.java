package com.hans.localirk.activiteis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.hans.localirk.R;
import com.hans.localirk.fragments.FragmentLocalize;
import com.hans.localirk.interfaces.OnAsyncTaskListener;
import com.hans.localirk.rest.RestClient;
import com.hans.localirk.utils.AsyncResponse;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_localize).setOnClickListener(this);
    }

    public void logOut(View view) {
        RestClient.logout(new OnAsyncTaskListener() {
            @Override
            public void onTaskBegin() {
            }

            @Override
            public void onTaskComplete(boolean isComplete, String message) {
                AsyncResponse response = new AsyncResponse(null);
                if (isComplete) {
                    response.clearToken();

                    Toast.makeText(getApplicationContext(), "logout success", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(getBaseContext(), ActivityLogin.class));
                    finish();
                } else {
                    response.showAlert(getSupportFragmentManager());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_localize:
                Fragment fragment = new FragmentLocalize();

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
                break;
        }
    }
}
