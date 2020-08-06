package com.dds.core.DTC;

import com.dds.objects.Budget;
import com.google.gson.annotations.SerializedName;

public class BudgetDTC {
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private int price;
    @SerializedName("date")
    private String date;
    @SerializedName("id")
    private String id;
    @SerializedName("type")
    private String type;

    public Budget toBudget(){
        return new Budget(id,name,""+price,date);
    }
}
