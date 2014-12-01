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

public class ActivityEnterAsGuest extends ActionBarActivity
        implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    EditText mName, mAge;
    Spinner mSpinnerAgeGroup;

    private int mGender, mAgeGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_as_guest);

        mName = (EditText) findViewById(R.id.txt_asGuest_name);
        mAge = (EditText) findViewById(R.id.txt_asGuest_age);
        mAge.setOnClickListener(this);
        mSpinnerAgeGroup = (Spinner) findViewById(R.id.spinner);
        mSpinnerAgeGroup.setOnItemSelectedListener(new SpinnerSelectedListener());

        findViewById(R.id.bt_enter).setOnClickListener(this);

        ((RadioGroup) findViewById(R.id.asGuest_radGroup)).setOnCheckedChangeListener(this);

        mGender = 1;
        mAgeGroup = 0;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_enter:
                enter();
                break;
            case R.id.txt_asGuest_age:
                mAge.setError(null);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.asGuest_radButton_m:
                mGender = 1;
                break;
            case R.id.asGuest_radButton_f:
                mGender = 2;
                break;
        }
    }

    private void enter() {
        String name = String.valueOf(mName.getText());
        String age = String.valueOf(mAge.getText());
        String ageGroup = String.valueOf(mAgeGroup);
        String gender = String.valueOf(mGender);

        boolean isValid = !age.equals("") || !ageGroup.equals("0");
        if (isValid) {
            RestClient.enterAsGuest(name, age, ageGroup, gender, new OnAsyncTaskListener() {
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
            mAge.setError(getString(R.string.fieldNotBeEmpty));
        }
    }

    private class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mAgeGroup = position;
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }
}