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
    public void addBudget(Budget budget) {
        this.budgets.add(budget);
        if(feedback != null)
            feedback.showMessage("Данные добавлены");
    }

    @Override
    public void addBudget(List<Budget> budgets) {
        this.budgets.addAll(budgets);
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
        budgets.add(new Budget("Зарплата июнь", "70000 ₽"));
        budgets.add(new Budget("Премия", "7000 ₽"));
        budgets.add(new Budget("Олег наконец-то вернул долг", "300000 ₽"));

    }

    private void fillBudgetCreditData(){
        budgets.add(new Budget("Молоко", "70 ₽"));
        budgets.add(new Budget("Зубная щётка", "120 ₽"));
        budgets.add(new Budget("Сковородка с антипригарным покрытием", "1370 ₽"));
    }

    @Override
    public void InitFeedback(IViewFeedback feedback) {
        this.feedback = feedback;
    }

    //endregion
}
