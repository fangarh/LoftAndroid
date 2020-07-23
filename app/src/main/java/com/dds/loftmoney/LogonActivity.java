package com.dds.loftmoney;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LogonActivity extends AppCompatActivity {

    private void initListeners(){
        findViewById(R.id.btnEnter).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent newActivity = new Intent(getApplicationContext(), AddItemActivity.class);

                startActivity(newActivity);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        initListeners();
    }
}