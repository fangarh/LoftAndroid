package com.dds.loftmoney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddItemActivity extends AppCompatActivity {
    //region private members declaration

    private EditText name, price;
    private Button applyBtn;
    private TextView id;

    //endregion

    //region private logic

    private void initListeners(){
        applyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               Intent intent = new Intent();
               intent.putExtra("BudgetName", name.getText().toString());
               intent.putExtra("BudgetPrice", price.getText().toString());
               intent.putExtra("Id", id.getText());
               setResult(RESULT_OK, intent);
               finish();
            }
        });
    }

    private void initFormElements(){
        id = findViewById(R.id.addItemId);
        name = findViewById(R.id.addItemNameInput);
        price = findViewById(R.id.addItemPriceInput);
        applyBtn = findViewById(R.id.addItemAddBtn);
    }

    private void getFromExtras(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString("Id").toString();
            String price = extras.getString("BudgetPrice");
            String name = extras.getString("BudgetName");

            applyBtn.setText(R.string.addItemButtonTextAtEdit);

            this.id.setText(id);
            this.price.setText(price);
            this.name.setText(name);
        }else
            applyBtn.setText(R.string.addItemButtonText);
    }

    //endregion

    //region overrided members

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        initFormElements();
        initListeners();

        getFromExtras();
    }

    //endregion
}