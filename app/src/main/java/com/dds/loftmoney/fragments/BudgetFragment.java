package com.dds.loftmoney.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dds.loftmoney.AddItemActivity;
import com.dds.loftmoney.R;
import com.dds.loftmoney.adapters.BudgetAdapter;
import com.dds.loftmoney.events.BudgetRowClickEventArgs;
import com.dds.loftmoney.events.IBudgetRowClick;
import com.dds.loftmoney.objects.BudgetRow;

import java.util.ArrayList;
import java.util.List;

public class BudgetFragment extends Fragment {
    //region ctor...


    public BudgetFragment() {
    }

    public BudgetFragment(Boolean debit) {
        if(!debit){
            color = R.color.creditColor;
            isDebit = false;
        }
    }

    //endregion

    //region private members

    private RecyclerView recyclerList;

    private Integer color = R.color.debitColor;
    private boolean isDebit = true;
    private BudgetAdapter budget = new BudgetAdapter();
    private static final int ADD_ITEM_ACTIVITY_REQUEST_CODE  = 0x000001;
    private static final int EDIT_ITEM_ACTIVITY_REQUEST_CODE = 0x000002;

    private BudgetRow editingRow = null;
    private Integer editingRowId = -1;

    //endregion

    //region private logic

    private void fillView(View view){
        recyclerList = view.findViewById(R.id.mainActivityBudgetRecyclerList);
    }

    private void setEvents(View view){
        budget.setOnClick(new IBudgetRowClick() {
            @Override
            public void onBudgetRowClick(BudgetRowClickEventArgs e) {
                Intent intent = new Intent(getContext(), AddItemActivity.class);

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
                Intent intent = new Intent(getContext(), AddItemActivity.class);

                startActivityForResult(intent, ADD_ITEM_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    private List<BudgetRow> fillBudgetDebitData(){
        List<BudgetRow> rows = new ArrayList<BudgetRow>();

        rows.add(new BudgetRow("Зарплата июнь", "70000 ₽", color));
        rows.add(new BudgetRow("Премия", "7000 ₽", color));
        rows.add(new BudgetRow("Олег наконец-то вернул долг", "300000 ₽", color));

        return rows;
    }

    private List<BudgetRow> fillBudgetCreditData(){
        List<BudgetRow> rows = new ArrayList<BudgetRow>();

        rows.add(new BudgetRow("Молоко", "70 ₽", color));
        rows.add(new BudgetRow("Зубная щётка", "120 ₽", color));
        rows.add(new BudgetRow("Сковородка с антипригарным покрытием", "1370 ₽", color));
        return rows;
    }

    //endregion

    //region overrided members

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_budget, null);

        fillView(fragment);
        setEvents(fragment);

        recyclerList.setAdapter(budget);
        recyclerList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

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
                editingRow = new BudgetRow();
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
