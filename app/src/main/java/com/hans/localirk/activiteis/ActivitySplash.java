package com.hans.localirk.activiteis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.hans.localirk.R;
import com.hans.localirk.interfaces.OnAsyncTaskListener;
import com.hans.localirk.rest.RestClient;
import com.hans.localirk.utils.AsyncResponse;
import com.hans.localirk.utils.TokenStore;

public class ActivitySplash extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String token = TokenStore.getInstance().getToken();

        if (token != null) {
            RestClient.loginSession(new OnAsyncTaskListener() {
                @Override
                public void onTaskBegin() {
                }

                @Override
                public void onTaskComplete(boolean isComplete, String message) {
                    AsyncResponse response = new AsyncResponse(message);
                    if (response.ifSuccess()) {
                        response.saveToken();

                        Toast.makeText(getApplicationContext(), "Sing up success", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(getBaseContext(), MainActivity.class));
                    } else {
                        response.showAlert(getSupportFragmentManager());
                    }
                }
            });
        } else {
            startActivity(new Intent(this, ActivityLogin.class));
            finish();
        }
    }
}
