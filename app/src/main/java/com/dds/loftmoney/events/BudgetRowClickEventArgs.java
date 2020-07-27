package com.dds.loftmoney.events;

import com.dds.objects.Budget;

public class BudgetRowClickEventArgs {
    //region Private members

    private Budget rowData;
    private Integer rowId;

    //endregion

    //region Ctor ...

    public BudgetRowClickEventArgs(Budget rowData, Integer rowId) {
        this.rowData = rowData;
        this.rowId = rowId;
    }

    //endregion

    //region Public getters/setters

    public Budget getRowData() {
        return rowData;
    }

    public void setRowData(Budget rowData) {
        this.rowData = rowData;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    //endregion
}
