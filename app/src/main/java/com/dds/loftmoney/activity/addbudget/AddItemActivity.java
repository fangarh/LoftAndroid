package com.dds.loftmoney.activity.addbudget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dds.loftmoney.R;

public class AddItemActivity extends AppCompatActivity {
    //region private members declaration

    private EditText name, price;
    private Button applyBtn;
    private TextView id;
    private Integer colorId = R.color.debitColor;

    private String nameStr, priceStr;

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

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nameStr = charSequence.toString();
                setElementsEnabled();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                priceStr = charSequence.toString();
                setElementsEnabled();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setElementsEnabled() {
        Boolean btnEnabled = (nameStr != null && !nameStr.isEmpty()) &&
                             (priceStr != null && !priceStr.isEmpty());

        applyBtn.setEnabled(btnEnabled);
        //applyBtn.setTextColor(ContextCompat.getColor(applyBtn.getContext(), btnEnabled ? R.color.addBudgetBtnColor : R.color.inactiveColor));
    }

    private void initFormElements(){
        id = findViewById(R.id.addItemId);
        name = findViewById(R.id.addItemNameInput);
        price = findViewById(R.id.addItemPriceInput);
        applyBtn = findViewById(R.id.addItemAddBtn);
    }

    private void getFromExtras(){
        Bundle extras = getIntent().getExtras();
        applyBtn.setText(R.string.addItemButtonText);

        if (extras != null) {
            String id = extras.getString("Id");
            colorId = extras.getInt("color");

            resetElementsColor();

            if(id != null && !id.isEmpty()){
                String price = extras.getString("BudgetPrice");
                String name = extras.getString("BudgetName");

                applyBtn.setText(R.string.addItemButtonTextAtEdit);

                this.id.setText(id);
                this.price.setText(price);
                this.name.setText(name);
            }
        }

    }

    private void resetElementsColor() {
        name.setTextColor(ContextCompat.getColor(name.getContext(), colorId));
        price.setTextColor(ContextCompat.getColor(price.getContext(), colorId));
        //applyBtn.setTextColor(ContextCompat.getColor(applyBtn.getContext(), colorId));
    }

    //endregion

    //region overridden members

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        initFormElements();
        initListeners();

        getFromExtras();
        setElementsEnabled();
    }

    //endregion
}