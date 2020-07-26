package com.dds.loftmoney;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dds.objects.Budget;

import java.util.ArrayList;
import java.util.List;

public class BudgetFragment extends Fragment {

    //region ctor...

    public BudgetFragment(Boolean debit) {
        if(!debit){
            color = R.color.creditColor;
            isDebit = false;
        }
    }

    //endregion

    //region private members

    private RecyclerView recyclerList;
    private Context context;

    private Integer color = R.color.debitColor;
    private boolean isDebit = true;
    private BudgetAdapter budget = new BudgetAdapter();
    private static final int ADD_ITEM_ACTIVITY_REQUEST_CODE  = 0x000001;
    private static final int EDIT_ITEM_ACTIVITY_REQUEST_CODE = 0x000002;

    private Budget editingRow = null;
    private Integer editingRowId = -1;

    //endregion

    //region private logic

    private void fillView(View view){
        context = getContext();
        recyclerList = view.findViewById(R.id.mainActivityBudgetRecyclerList);
    }

    private void setEvents(View view){
        budget.setOnClick(new IBudgetRowClick() {
            @Override
            public void onBudgetRowClick(BudgetRowClickEventArgs e) {
                Intent intent = new Intent(context, AddItemActivity.class);

                intent.putExtra("BudgetName", e.getRowData().getName());
                intent.putExtra("BudgetPrice", e.getRowData().getPrice());
                intent.putExtra("Id", e.getRowData().getId().toString());
                startActivityForResult(intent, EDIT_ITEM_ACTIVITY_REQUEST_CODE);
                // startActivity(intent);
                editingRow = e.getRowData();
                editingRowId = e.getRowId();
            }
        });

        view.findViewById(R.id.mainActivityAddBudgetRow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddItemActivity.class);

                startActivityForResult(intent, ADD_ITEM_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    //region test data fill
    private List<Budget> fillBudgetDebitData(){
        List<Budget> rows = new ArrayList<Budget>();

        rows.add(new Budget("Зарплата июнь", "70000 ₽", color));
        rows.add(new Budget("Премия", "7000 ₽", color));
        rows.add(new Budget("Олег наконец-то вернул долг", "300000 ₽", color));

        return rows;
    }

    private List<Budget> fillBudgetCreditData(){
        List<Budget> rows = new ArrayList<Budget>();

        rows.add(new Budget("Молоко", "70 ₽", color));
        rows.add(new Budget("Зубная щётка", "120 ₽", color));
        rows.add(new Budget("Сковородка с антипригарным покрытием", "1370 ₽", color));
        return rows;
    }
    //endregion
    //endregion

    //region overrided members

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_budget, null);

        fillView(fragment);
        setEvents(fragment);

        recyclerList.setAdapter(budget);
        recyclerList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        RecyclerView.ItemDecoration dividerItemDecoration = new BudgetRowsDividerDecorator(ContextCompat.getDrawable(context, R.drawable.budget_row_divider));
        recyclerList.addItemDecoration(dividerItemDecoration);

        budget.Clear();
        budget.AddRange(isDebit?fillBudgetDebitData():fillBudgetCreditData());

        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_ITEM_ACTIVITY_REQUEST_CODE && editingRow != null) {
            if (resultCode == Activity.RESULT_OK) {
                editingRow.setPrice(data.getStringExtra("BudgetPrice"));
                editingRow.setName(data.getStringExtra("BudgetName"));
                budget.notifyItemChanged(editingRowId);
            }
            editingRow = null;
            editingRowId = -1;
        }

        if(requestCode == ADD_ITEM_ACTIVITY_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK) {
                editingRow = new Budget();
                editingRow.setPrice(data.getStringExtra("BudgetPrice"));
                editingRow.setName(data.getStringExtra("BudgetName"));
                editingRow.setColor(color);
                budget.Add(editingRow);
                editingRow = null;
            }
        }
    }

    //endregion
}
