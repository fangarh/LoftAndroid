package com.dds.core.presenters.logon;

import com.dds.core.viewbehavior.logon.ILogonView;

public interface ILogonPresenter {
    void setView(ILogonView view);
    void performLogin();
}
