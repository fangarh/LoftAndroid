package com.dds.loftmoney.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dds.loftmoney.R;
import com.dds.loftmoney.objects.BudgetRow;

import java.util.ArrayList;
import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {
    private List<BudgetRow> _rows = new ArrayList<BudgetRow>();

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
        return new BudgetViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_budget, parent, false));
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

        private void findViewElements(View parent){
            _nameView = parent.findViewById(R.id.budgetRowItemName);
            _priceView = parent.findViewById(R.id.budgetRowItemPrice);
        }

        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            findViewElements(itemView);
        }

        public void bind(BudgetRow row){
            _nameView.setText(row.getName());
            _priceView.setText(row.getPrice());
            _priceView.setTextColor(ContextCompat.getColor(_priceView.getContext(), row.getColor()));
        }
    }
}
