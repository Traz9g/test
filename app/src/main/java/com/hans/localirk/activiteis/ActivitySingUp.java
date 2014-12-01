package com.hans.localirk.activiteis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.hans.localirk.R;
import com.hans.localirk.interfaces.OnAsyncTaskListener;
import com.hans.localirk.rest.RestClient;
import com.hans.localirk.utils.AsyncResponse;

public class ActivitySingUp extends ActionBarActivity
        implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{
    private EditText mName, mPassword, mPerPassword, mAge, mEmail;
    private Spinner mAgeGroupSpinner;

    private int mGender, mAgeGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mName = (EditText) findViewById(R.id.txt_SingUp_name);
        mName.setOnClickListener(this);
        mPassword = (EditText) findViewById(R.id.txt_SingUp_pass);
        mPassword.setOnClickListener(this);
        mPerPassword = (EditText) findViewById(R.id.txt_SingUp_repPass);
        mPerPassword.setOnClickListener(this);
        mEmail = (EditText) findViewById(R.id.txt_SingUp_email);
        mEmail.setOnClickListener(this);
        mAge = (EditText) findViewById(R.id.txt_SingUp_age);
        mAge.setOnClickListener(this);
        mAgeGroupSpinner = (Spinner) findViewById(R.id.ageSpinner);
        mAgeGroupSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        ((RadioGroup) findViewById(R.id.singUp_radioGroup)).setOnCheckedChangeListener(this);
        findViewById(R.id.bt_singUp).setOnClickListener(this);

        mGender = 1;
        mAgeGroup = 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_singUp:
                singUp();
                break;
            case R.id.txt_SingUp_name:
                mName.setError(null);
                break;
            case R.id.txt_SingUp_pass:
                mPassword.setError(null);
                break;
            case R.id.txt_SingUp_repPass:
                mPerPassword.setError(null);
                break;
            case R.id.txt_SingUp_email:
                mEmail.setError(null);
                break;
            case R.id.txt_SingUp_age:
                mAge.setError(null);
                break;
        }
    }

    private void singUp() {
        String name = String.valueOf(mName.getText());
        String pass = String.valueOf(mPassword.getText());
        String repPass = String.valueOf(mPerPassword.getText());
        String email = String.valueOf(mEmail.getText());
        String age = String.valueOf(mAge.getText());
        String ageGroup = String.valueOf(mAgeGroup);
        String gender = String.valueOf(mGender);

        if (name.equals("")) {
            mName.setError(getString(R.string.fieldNotBeEmpty));
        } else if (pass.equals("")) {
            mPassword.setError(getString(R.string.fieldNotBeEmpty));
        } else if (repPass.equals("")) {
            mPerPassword.setError(getString(R.string.fieldNotBeEmpty));
        } else if (!pass.equals(repPass)) {
            mPerPassword.setError(getString(R.string.fieldPassInc));
        } else if (age.equals("") && ageGroup.equals("0")) {
            mAge.setError(getString(R.string.fieldNotBeEmpty));
        } else if (email.equals("")) {
            mEmail.setError(getString(R.string.fieldNotBeEmpty));
        } else {
            // TODO: avatar img
            RestClient.singUp(name, pass, email, age, ageGroup, gender, " ", new OnAsyncTaskListener() {
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
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.singUp_rBut_m:
                mGender = 1;
                break;
            case R.id.singUp_rBut_f:
                mGender = 2;
                break;
        }
    }

    private class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mAgeGroup = position;
        }

        public void onNothingSelected(AdapterView parent) {
        }
    }
}