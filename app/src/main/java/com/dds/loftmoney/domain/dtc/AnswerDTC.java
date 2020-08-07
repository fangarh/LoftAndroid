package com.dds.loftmoney.domain.dtc;

import com.google.gson.annotations.SerializedName;

public class AnswerDTC {
    @SerializedName("status")
    String status;
    @SerializedName("id")
    String id;

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }
}
