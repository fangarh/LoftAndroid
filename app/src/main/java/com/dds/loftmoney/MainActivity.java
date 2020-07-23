package com.dds.loftmoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.dds.loftmoney.adapters.BudgetAdapter;
import com.dds.loftmoney.objects.BudgetRow;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView _recyclerList;
    BudgetAdapter _budget = new BudgetAdapter();

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

       // _budget.Clear();
        _budget.AddRange(fillBudgetDebitData());

    }
}