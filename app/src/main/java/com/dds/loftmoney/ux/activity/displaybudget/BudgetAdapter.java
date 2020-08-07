package com.dds.loftmoney.ux.activity.displaybudget;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dds.loftmoney.R;
import com.dds.loftmoney.utils.faces.IBudgetAccess;
import com.dds.loftmoney.ux.events.IBudgetRowClick;
import com.dds.loftmoney.domain.objects.Budget;

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

    public void Add(Budget row, Boolean debit, String token){
        rows.addBudget(row, debit, token);
        notifyDataSetChanged();
    }

    public void Update(Integer rowId, Budget row){
        rows.updateRow(rowId, row);
        notifyItemChanged(rowId);
    }

    public void fill(Boolean debit, Integer color, String token){
        this.color = color;
        rows.fill(debit, token);
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
