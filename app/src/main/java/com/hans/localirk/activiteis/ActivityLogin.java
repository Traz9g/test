package com.hans.localirk.activiteis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hans.localirk.R;
import com.hans.localirk.interfaces.OnAsyncTaskListener;
import com.hans.localirk.rest.RestClient;
import com.hans.localirk.utils.AsyncResponse;

public class ActivityLogin extends FragmentActivity implements View.OnClickListener {
    EditText mLogin, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLogin = (EditText) findViewById(R.id.txt_login);
        mPassword = (EditText) findViewById(R.id.txt_pass);

        findViewById(R.id.bt_singUp).setOnClickListener(this);
        findViewById(R.id.bt_asGuest).setOnClickListener(this);
        findViewById(R.id.bt_login).setOnClickListener(this);
        findViewById(R.id.txt_login).setOnClickListener(this);
        findViewById(R.id.txt_pass).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                login();
                break;
            case R.id.bt_singUp:
                startActivity(new Intent(this, ActivitySingUp.class));
                break;
            case R.id.bt_asGuest:
                startActivity(new Intent(this, ActivityEnterAsGuest.class));
                break;
            case R.id.txt_login:
                mLogin.setError(null);
                break;
            case R.id.txt_pass:
                mPassword.setError(null);
                break;
        }
    }

    private void login() {
        String login = String.valueOf(mLogin.getText());
        String password = String.valueOf(mPassword.getText());

        if (login.equals("")) {
            mLogin.setError(getString(R.string.fieldNotBeEmpty));
        } else if (password.equals("")) {
            mPassword.setError(getString(R.string.fieldNotBeEmpty));
        } else {
            RestClient.login(login, password, new OnAsyncTaskListener() {
                @Override
                public void onTaskBegin() {
                }

                @Override
                public void onTaskComplete(boolean isComplete, String message) {
                    AsyncResponse response = new AsyncResponse(message);
                    if (response.ifSuccess()) {
                        response.saveToken();

                        Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getBaseContext(), MainActivity.class));
                    } else {
                        response.showAlert(getSupportFragmentManager());
                    }
                }
            });
        }
    }
}