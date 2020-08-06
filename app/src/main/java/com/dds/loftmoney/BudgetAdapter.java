package com.dds.loftmoney;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dds.core.faces.IBudgetAccess;
import com.dds.loftmoney.events.BudgetRowClickEventArgs;
import com.dds.loftmoney.events.IBudgetRowClick;
import com.dds.objects.Budget;

import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetViewHolder> {
    //region private members declaration

    private Integer color;

    //endregion

    //region ctor...

    public BudgetAdapter(IBudgetAccess rowsAccess) {
        rows = rowsAccess;
    }

    //endregion

    // region private members declaration

    private IBudgetAccess rows;
    private IBudgetRowClick onClick;

    //endregion

    //region data access logic

    public void Add(Budget row){
        rows.addBudget(row, true);
        notifyDataSetChanged();
    }

    public void Update(Integer rowId, Budget row){
        rows.updateRow(rowId, row);
        notifyItemChanged(rowId);
    }

    public void fill(Boolean debit, Integer color){
        this.color = color;
        rows.fill(debit);
    }

    //endregion

    //region set events

    public void setOnClick(IBudgetRowClick onClick) {
        this.onClick = onClick;
    }

    //endregion

    //region overrided members

    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BudgetViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_budget, parent, false), onClick);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        holder.bind(rows.get(position), position, color);
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    //endregion
}
