package com.dds.loftmoney.events;

import com.dds.loftmoney.objects.BudgetRow;

public class BudgetRowClickEventArgs {
    //region Private members

    private BudgetRow rowData;
    private Integer rowId;

    //endregion

    //region Ctor ...

    public BudgetRowClickEventArgs() {
    }

    public BudgetRowClickEventArgs(BudgetRow rowData, Integer rowId) {
        this.rowData = rowData;
        this.rowId = rowId;
    }

    //endregion

    //region Public getters/setters

    public BudgetRow getRowData() {
        return rowData;
    }

    public void setRowData(BudgetRow rowData) {
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
