package com.dds.loftmoney;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dds.loftmoney.events.BudgetRowClickEventArgs;
import com.dds.loftmoney.events.IBudgetRowClick;
import com.dds.objects.Budget;

public class BudgetViewHolder extends RecyclerView.ViewHolder{
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

    public void bind(final Budget row, final Integer rowId, Integer color){
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
        priceView.setTextColor(ContextCompat.getColor(priceView.getContext(), color));
    }
}