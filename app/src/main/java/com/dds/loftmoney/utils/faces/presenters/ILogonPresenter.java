package com.dds.loftmoney.utils.faces.presenters;


import com.dds.loftmoney.utils.faces.views.ILogonView;

public interface ILogonPresenter {
    void setView(ILogonView view);
    void performLogin();
}
