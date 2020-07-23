package com.dds.loftmoney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

public class AddItemActivity extends AppCompatActivity {
    private EditText _name, _price;
    private TextView _id;

    private void initListeners(){
        findViewById(R.id.addItemAddBtn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent newActivity = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(newActivity);
            }
        });
    }

    private void initFormElements(){
        _id = findViewById(R.id.addItemId);
        _name = findViewById(R.id.addItemNameInput);
        _price = findViewById(R.id.addItemPriceInput);
    }

    private void getFromExtras(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString("Id").toString();
            String price = extras.getString("BudgetPrice");
            String name = extras.getString("BudgetName");

            _id.setText(id);
            _price.setText(price);
            _name.setText(name);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        initListeners();
        initFormElements();
        getFromExtras();
    }
}