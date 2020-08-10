package com.dds.loftmoney.utils.logic;

import com.dds.loftmoney.R;
import com.dds.loftmoney.domain.objects.Budget;
import com.dds.loftmoney.utils.faces.presenters.IBudgetAccess;
import com.dds.loftmoney.utils.faces.views.IViewFeedback;

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
    public void addBudget(Budget budget, String token) {
        this.budgets.add(budget);
        if(feedback != null)
            feedback.showMessage(R.string.data_added_success);
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
    public void fill(String token) {
        if(true) fillBudgetDebitData();
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
    public void deleteRow(String rowId, String token) {

    }

    @Override
    public void deleteBudgetRows(List<String> ids, String token) {

    }

    @Override
    public Integer calcTotal() {
        return 0;
    }

    //endregion
}
