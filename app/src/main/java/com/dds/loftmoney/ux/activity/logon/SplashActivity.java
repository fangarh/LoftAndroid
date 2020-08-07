package com.dds.loftmoney.ux.activity.logon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.dds.loftmoney.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent newActivity = new Intent(getApplicationContext(), LogonActivity.class);

        startActivity(newActivity);
        finish();
    }
}