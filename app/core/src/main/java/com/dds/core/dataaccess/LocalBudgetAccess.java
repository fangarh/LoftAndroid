package com.dds.core.dataaccess;

import com.dds.core.faces.IBudgetAccess;
import com.dds.objects.Budget;

import java.util.ArrayList;
import java.util.List;

public class LocalBudgetAccess implements IBudgetAccess {
    //region private members declaration

    private List<Budget> budgets = new ArrayList<>();

    //endregion

    //region IBudgetAccess implementation

    @Override
    public List<Budget> getBudget() {
        return this.budgets;
    }

    @Override
    public void addBudget(Budget budget) {
        this.budgets.add(budget);
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
    public void fill(Boolean debit, Integer colorParam) {
        if(debit) fillBudgetDebitData(colorParam);
        else fillBudgetCreditData(colorParam);
    }

    //endregion
    //region test data fill
    private void fillBudgetDebitData(Integer color){
        budgets.add(new Budget("Зарплата июнь", "70000 ₽", color));
        budgets.add(new Budget("Премия", "7000 ₽", color));
        budgets.add(new Budget("Олег наконец-то вернул долг", "300000 ₽", color));

    }

    private void fillBudgetCreditData(Integer color){
        budgets.add(new Budget("Молоко", "70 ₽", color));
        budgets.add(new Budget("Зубная щётка", "120 ₽", color));
        budgets.add(new Budget("Сковородка с антипригарным покрытием", "1370 ₽", color));
    }

    //endregion
}
