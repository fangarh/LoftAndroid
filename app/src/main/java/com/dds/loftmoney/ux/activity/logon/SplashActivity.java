package com.dds.loftmoney.ux.activity.logon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.os.Bundle;

import com.dds.loftmoney.R;
import com.dds.loftmoney.ux.activity.displaybudget.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final String token = PreferenceManager.getDefaultSharedPreferences(this).getString(MainActivity.TOKEN, "");

        if(token != null && !token.isEmpty()) {
            Intent newActivity = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(newActivity);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.fade_alpha_anim);
        }else {
            Intent newActivity = new Intent(getApplicationContext(), LogonActivity.class);

            startActivity(newActivity);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.fade_alpha_anim);
        }
    }


}