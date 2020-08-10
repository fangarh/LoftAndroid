package com.dds.loftmoney.utils.faces.presenters;


import com.dds.loftmoney.domain.objects.Budget;
import com.dds.loftmoney.utils.faces.views.IViewFeedback;

import java.util.List;

public interface IBudgetAccess {
    void InitFeedback(IViewFeedback feedback);

    List<Budget> getBudget();

    Budget get(Integer position);

    void addBudget(Budget budget, String token);

    void updateRow(Integer rowId, Budget budgetRow);

    void deleteBudgetRows(List<String> ids, String token);

    void deleteRow(String rowId, String token);

    Integer size();

    void fill(String token);

    Integer calcTotal();

}
