package com.dds.loftmoney;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dds.core.faces.IBudgetAccess;
import com.dds.objects.Budget;

import java.util.ArrayList;
import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {

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
        rows.addBudget(row);
        notifyDataSetChanged();
    }

    public void AddRange(List<Budget> rows){
        this.rows.addBudget(rows);
        notifyDataSetChanged();
    }

    public void fill(Boolean debit, Integer color){
        rows.fill(debit, color);
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
        holder.bind(rows.get(position), position);
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    static class BudgetViewHolder extends RecyclerView.ViewHolder{
        TextView nameView, priceView;
        IBudgetRowClick onClick;

        private void findViewElements(View parent){
            nameView = parent.findViewById(R.id.budgetRowItemName);
            priceView = parent.findViewById(R.id.budgetRowItemPrice);
        }

        public BudgetViewHolder(@NonNull View itemView, @Nullable IBudgetRowClick clickEvent) {
            super(itemView);
            findViewElements(itemView);
            onClick = clickEvent;
        }

        public void bind(final Budget row, final Integer rowId){
            if(onClick != null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClick.onBudgetRowClick(new BudgetRowClickEventArgs(row, rowId));
                    }
                });
            }

            nameView.setText(row.getName());
            priceView.setText(row.getPrice());
            priceView.setTextColor(ContextCompat.getColor(priceView.getContext(), row.getColor()));
        }
    }

    //endregion
}
