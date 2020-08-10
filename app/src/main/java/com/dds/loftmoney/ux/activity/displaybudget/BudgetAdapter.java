package com.dds.loftmoney.ux.activity.displaybudget;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dds.loftmoney.R;
import com.dds.loftmoney.utils.faces.presenters.IBudgetAccess;
import com.dds.loftmoney.utils.faces.views.IViewFeedback;
import com.dds.loftmoney.ux.events.IBudgetRowClick;
import com.dds.loftmoney.domain.objects.Budget;

import java.util.ArrayList;
import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetViewHolder> {
    //region private members declaration

    private Integer color;
    private IBudgetAccess rows;
    private IBudgetRowClick onClick;
    private SparseBooleanArray selectedItems;

    //endregion

    //region ctor...

    public BudgetAdapter(IBudgetAccess rowsAccess) {
        rows = rowsAccess;
        selectedItems = new SparseBooleanArray();
    }

    //endregion

    //region data access logic
    //delete and selection logic

    public void clearSelections(){
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public void toggleSelection(int position){
        selectedItems.put(position, !selectedItems.get(position));
        notifyDataSetChanged();
    }

    public void clearSelection(int position){
        selectedItems.put(position, false);
        notifyDataSetChanged();
    }

    public int getSelectionsCount(){
        int result = 0;

        for(int i = 0; i < selectedItems.size(); i ++){
            if(selectedItems.get(selectedItems.keyAt(i))){
                result ++;
            }
        }

        return result;
    }

    private List<String> getSelectedElements(){
        List<String> result = new ArrayList<>();

        for(int i = 0; i < selectedItems.size(); i ++){
            if(selectedItems.get(selectedItems.keyAt(i))){
                result.add(rows.get(selectedItems.keyAt(i)).getId());
            }
        }

        return result;
    }

    public void deleteSelected(String token){
        rows.deleteBudgetRows(getSelectedElements(), token);
    }

    //endregion

    public void Add(Budget row, String token){
        rows.addBudget(row, token);
        notifyDataSetChanged();
    }

    public void Update(Integer rowId, Budget row){
        rows.updateRow(rowId, row);
        notifyItemChanged(rowId);
    }

    public void fill(Integer color, String token){
        this.color = color;
        rows.fill(token);
    }

    //endregion

    //region set events

    public void setOnClick(IBudgetRowClick onClick) {
        this.onClick = onClick;
    }

    public void resetFeedback(IViewFeedback feedback){
        this.rows.InitFeedback(feedback);
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
        holder.bind(rows.get(position), position, color, selectedItems.get(position));
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    //endregion
}
