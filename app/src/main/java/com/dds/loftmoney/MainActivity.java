package com.dds.loftmoney;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dds.loftmoney.adapters.BudgetAdapter;
import com.dds.loftmoney.events.IBudgetRowClick;
import com.dds.loftmoney.objects.BudgetRow;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView _recyclerList;

    private BudgetAdapter _budget = new BudgetAdapter();
    private static final int ADD_ITEM_ACTIVITY_REQUEST_CODE  = 0x000001;
    private static final int EDIT_ITEM_ACTIVITY_REQUEST_CODE = 0x000002;

    private BudgetRow _editingRow = null;

    private void fillView(){
        _recyclerList = findViewById(R.id.mainActivityBudgetRecyclerList);
    }

    private void setEvents(){
        _budget.setOnClick(new IBudgetRowClick() {
            @Override
            public void onBudgetRowClick(BudgetRow row) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);

                intent.putExtra("BudgetName", row.getName());
                intent.putExtra("BudgetPrice", row.getPrice());
                intent.putExtra("Id", row.getId().toString());
                startActivityForResult(intent, EDIT_ITEM_ACTIVITY_REQUEST_CODE);
                // startActivity(intent);
                _editingRow = row;
            }
        });

        findViewById(R.id.mainActivityAddBudgetRow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
/*
                intent.putExtra("BudgetName", "");
                intent.putExtra("BudgetPrice", "0");
                intent.putExtra("Id", "");
*/
                startActivityForResult(intent, ADD_ITEM_ACTIVITY_REQUEST_CODE);

               // _editingRow = new BudgetRow();
            }
        });
    }

    private List<BudgetRow> fillBudgetDebitData(){
        List<BudgetRow> rows = new ArrayList<BudgetRow>();

        rows.add(new BudgetRow("Молоко", "70 ₽", R.color.dark_sky_blue));
        rows.add(new BudgetRow("Зубная щётка", "120 ₽", R.color.dark_sky_blue));
        rows.add(new BudgetRow("Сковородка с антипригарным покрытием", "1370 ₽", R.color.greeny_blue));

        return rows;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillView();
        setEvents();

        _recyclerList.setAdapter(_budget);
        _recyclerList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));

        _budget.Clear();
        _budget.AddRange(fillBudgetDebitData());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_ITEM_ACTIVITY_REQUEST_CODE && _editingRow != null) {
            if (resultCode == RESULT_OK) {
                _editingRow.setPrice(data.getStringExtra("BudgetPrice"));
                _editingRow.setName(data.getStringExtra("BudgetName"));
                _budget.notifyDataSetChanged();
                _budget.updateById(data.getStringExtra("Id"),data.getStringExtra("BudgetName"),data.getStringExtra("BudgetPrice"));
            }
            _editingRow = null;
        }

        if(requestCode == ADD_ITEM_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                _editingRow = new BudgetRow();
                _editingRow.setPrice(data.getStringExtra("BudgetPrice"));
                _editingRow.setName(data.getStringExtra("BudgetName"));
                _editingRow.setColor(R.color.greeny_blue);
                _budget.Add(_editingRow);
                _editingRow = null;
            }
        }
    }
}