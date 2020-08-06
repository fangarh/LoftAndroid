package com.dds.core.DTC;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoneyDTC {
    @SerializedName("status")
    String status;

    @SerializedName("data")
    List<BudgetDTC> budgetItems;

    public String getStatus() {
        return status;
    }

    public List<BudgetDTC> getBudgetItems() {
        return budgetItems;
    }
}
