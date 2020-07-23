package com.dds.loftmoney.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dds.loftmoney.R;
import com.dds.loftmoney.events.IBudgetRowClick;
import com.dds.loftmoney.objects.BudgetRow;

import java.util.ArrayList;
import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {
    private List<BudgetRow> _rows = new ArrayList<BudgetRow>();
    private IBudgetRowClick _onClick;

    public void setOnClick(IBudgetRowClick onClick) {
        _onClick = onClick;
    }

    public void Clear(){
        _rows.clear();
        notifyDataSetChanged();
    }

    public void Add(BudgetRow row){
        _rows.add(row);
        notifyDataSetChanged();
    }

    public void AddRange(List<BudgetRow> rows){
        _rows.addAll(rows);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BudgetViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_budget, parent, false), _onClick);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        holder.bind(_rows.get(position));
    }

    @Override
    public int getItemCount() {
        return _rows.size();
    }

    static class BudgetViewHolder extends RecyclerView.ViewHolder{
        TextView _nameView, _priceView;
        IBudgetRowClick _onClick;

        private void findViewElements(View parent){
            _nameView = parent.findViewById(R.id.budgetRowItemName);
            _priceView = parent.findViewById(R.id.budgetRowItemPrice);
        }

        public BudgetViewHolder(@NonNull View itemView, @Nullable IBudgetRowClick clickEvent) {
            super(itemView);
            findViewElements(itemView);
            _onClick = clickEvent;
        }

        public void bind(final BudgetRow row){
            if(_onClick != null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        _onClick.onBudgetRowClick(row);
                    }
                });
            }

            _nameView.setText(row.getName());
            _priceView.setText(row.getPrice());
            _priceView.setTextColor(ContextCompat.getColor(_priceView.getContext(), row.getColor()));
        }
    }
}
