package com.dds.loftmoney.utils.faces.views;

public interface IViewFeedback {
    void showMessage(String message);
    void showMessage(Integer resource);
    String getIid();
    void DataUpdated();
}
