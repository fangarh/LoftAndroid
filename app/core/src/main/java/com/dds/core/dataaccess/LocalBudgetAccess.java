package com.dds.core.dataaccess;

import com.dds.core.R;
import com.dds.core.faces.IBudgetAccess;
import com.dds.core.faces.IViewFeedback;
import com.dds.objects.Budget;

import java.util.ArrayList;
import java.util.List;

public class LocalBudgetAccess implements IBudgetAccess {
    //region private members declaration

    private List<Budget> budgets = new ArrayList<>();
    private IViewFeedback feedback = null;

    //endregion

    //region IBudgetAccess implementation

    @Override
    public List<Budget> getBudget() {
        return this.budgets;
    }

    @Override
    public void addBudget(Budget budget, Boolean debit) {
        this.budgets.add(budget);
        if(feedback != null)
            feedback.showMessage("Данные добавлены");
    }

    @Override
    public Budget get(Integer position) {
        return this.budgets.get(position);
    }

    @Override
    public Integer size() {
        return this.budgets.size();
    }

    @Override
    public void fill(Boolean debit) {
        if(debit) fillBudgetDebitData();
        else fillBudgetCreditData();
    }

    //endregion
    //region test data fill
    private void fillBudgetDebitData(){
        budgets.add(new Budget("1", "Зарплата июнь", "70000", "" ));
        budgets.add(new Budget("2", "Премия", "7000", "" ));
        budgets.add(new Budget("3", "Олег наконец-то вернул долг", "300000", ""));
        if(feedback != null) feedback.DataUpdated();
    }

    private void fillBudgetCreditData(){
        budgets.add(new Budget("1", "Молоко", "70", "" ));
        budgets.add(new Budget("2", "Зубная щётка", "120", ""));
        budgets.add(new Budget("3","Сковородка с антипригарным покрытием", "1370", "" ));
        if(feedback != null) feedback.DataUpdated();
    }

    @Override
    public void InitFeedback(IViewFeedback feedback) {
        this.feedback = feedback;
    }

    @Override
    public void updateRow(Integer rowId, Budget budgetRow) {

    }

    @Override
    public void deleteRow(Integer rowId) {

    }

    //endregion
}
