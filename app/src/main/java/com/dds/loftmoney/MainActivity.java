package com.dds.loftmoney;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

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

    private void fillView(){
        _recyclerList = findViewById(R.id.mainActivityBudgetRecyclerList);
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

        _recyclerList.setAdapter(_budget);
        _recyclerList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));

        _budget.setOnClick(new IBudgetRowClick() {
            @Override
            public void onBudgetRowClick(BudgetRow row) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);

                intent.putExtra("BudgetName", row.getName());
                intent.putExtra("BudgetPrice", row.getPrice());
                intent.putExtra("Id", row.getId().toString());

                startActivity(intent);
            }
        });
        _budget.Clear();
        _budget.AddRange(fillBudgetDebitData());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}