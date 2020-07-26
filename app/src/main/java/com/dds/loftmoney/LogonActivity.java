package com.dds.loftmoney;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LogonActivity extends AppCompatActivity {

    //region private logic

    private void initListeners(){
        findViewById(R.id.btnEnter).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent newActivity = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(newActivity);
            }
        });
    }

    //endregion

    //region overrided members

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        initListeners();
    }

    //endregion
}