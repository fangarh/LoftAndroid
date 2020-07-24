package com.dds.loftmoney.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dds.loftmoney.R;
import com.dds.loftmoney.events.BudgetRowClickEventArgs;
import com.dds.loftmoney.events.IBudgetRowClick;
import com.dds.loftmoney.objects.BudgetRow;

import java.util.ArrayList;
import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {

    private List<BudgetRow> rows = new ArrayList<BudgetRow>();
    private IBudgetRowClick onClick;

    public void setOnClick(IBudgetRowClick onClick) {
        this.onClick = onClick;
    }

    public void Clear(){
        rows.clear();
        notifyDataSetChanged();
    }

    public void Add(BudgetRow row){
        rows.add(row);
        notifyDataSetChanged();
    }

    public void updateById(String id, String name, String price){
        Log.e(">>>", id);

        for(int i = 0; i < rows.size(); i ++){
            Log.e(rows.get(i).getId().toString(), id);
            //TODO: Why not equals ?!?!?!?!?!
            if(rows.get(i).getId().toString().equals(id)){
                Log.w("!!", rows.get(i).getId().toString());
                rows.get(i).setName(name);
                rows.get(i).setPrice(price);
                notifyItemChanged(i);
                return;
            }
        }
    }

    public void AddRange(List<BudgetRow> rows){
        this.rows.addAll(rows);
        notifyDataSetChanged();
    }

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

        public void bind(final BudgetRow row, final Integer rowId){
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
}
