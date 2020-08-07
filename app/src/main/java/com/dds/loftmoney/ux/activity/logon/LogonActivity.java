package com.dds.loftmoney.ux.activity.logon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.dds.loftmoney.R;
import com.dds.loftmoney.utils.faces.ILogonView;
import com.dds.loftmoney.utils.logic.LogonPresenter;
import com.dds.loftmoney.ux.activity.displaybudget.MainActivity;


public class LogonActivity extends AppCompatActivity implements ILogonView {
    //region private members

    private Button logonBtn;
    private LogonPresenter logonPresenter;

    //endregion
    //region private logic

    private void initListeners(){
        logonBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                logonPresenter.performLogin();
            }
        });
    }

    private void initElements() {
        logonBtn = findViewById(R.id.btnEnter);
        logonPresenter = new LogonPresenter();
        logonPresenter.setView(this);
    }

    //endregion

    //region ILogonView

    @Override
    public void toggleSending(boolean isActive) {
        logonBtn.setVisibility(isActive ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successLogon(String token) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                LogonActivity.this).edit();
        editor.putString(MainActivity.TOKEN, token);
        editor.apply();

        Toast.makeText(getApplicationContext(), R.string.loginSuccess, Toast.LENGTH_SHORT).show();

        Intent newActivity = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(newActivity);
    }


    //endregion

    //region overrided members

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);

        initElements();
        initListeners();

        final String token = PreferenceManager.getDefaultSharedPreferences(this).getString(MainActivity.TOKEN, "");

        if(token != null && !token.isEmpty()) {
            Intent newActivity = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(newActivity);
            finish();
        }
    }

    //endregion
}