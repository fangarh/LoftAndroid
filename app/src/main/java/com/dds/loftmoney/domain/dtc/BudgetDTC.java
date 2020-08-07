package com.dds.loftmoney.domain.dtc;

import com.dds.loftmoney.domain.objects.Budget;
import com.google.gson.annotations.SerializedName;

public class BudgetDTC {
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private int price;
    @SerializedName("created_at")
    private String date;
    @SerializedName("id")
    private String id;
    @SerializedName("type")
    private String type;

    public Budget toBudget(){
        return new Budget(id,name,""+price,date);
    }
}
