package com.dds.loftmoney.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dds.core.DTC.AuthResponseDTC;
import com.dds.core.dataaccess.AuthProvider;
import com.dds.core.faces.IAuthProvider;
import com.dds.core.faces.IAuthResultProvider;
import com.dds.loftmoney.R;

import java.util.Random;

public class LogonActivity extends AppCompatActivity implements IAuthResultProvider {
    private IAuthProvider authProvider;
    //region private logic

    private void initListeners(){
        findViewById(R.id.btnEnter).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                /// TO MOVE IN PRESENTER
                String login = "dds" + (new Random()).nextInt();
                authProvider.InitResultActions(LogonActivity.this);
                authProvider.PerformLogin(login);

/*
                Intent newActivity = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(newActivity);*/
            }
        });
    }

    //endregion

    //region IAuthProvider

    @Override
    public void AuthResult(AuthResponseDTC result, String message) {
        if(result == null){
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            return;
        }

        if(result.getStatus().equals("success")) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                    LogonActivity.this).edit();
            editor.putString(MainActivity.TOKEN,result.getAccessToken());
            editor.apply();

            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

            Intent newActivity = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(newActivity);
        }
    }


    //endregion

    //region overrided members

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String token = PreferenceManager.getDefaultSharedPreferences(this).getString(MainActivity.TOKEN, "");

        if(token == null || token.isEmpty()) {
            authProvider = new AuthProvider();

            setContentView(R.layout.activity_logon);
            initListeners();
        }else{
            Intent newActivity = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(newActivity);
        }
    }

    //endregion
}