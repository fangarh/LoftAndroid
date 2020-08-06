package com.dds.core.faces;

import com.dds.objects.Budget;

import java.util.List;

public interface IBudgetAccess {
    void InitFeedback(IViewFeedback feedback);

    List<Budget> getBudget();

    Budget get(Integer position);

    void addBudget(Budget budget, Boolean debit, String token);

    void updateRow(Integer rowId, Budget budgetRow);

    void deleteRow(Integer rowId, String token);

    Integer size();

    void fill(Boolean debit, String token);
}
