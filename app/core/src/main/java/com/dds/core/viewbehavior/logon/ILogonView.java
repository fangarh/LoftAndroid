package com.dds.core.viewbehavior.logon;

public interface ILogonView {
    void toggleSending(boolean isActive);
    void showMessage(String message);
    void successLogon(String token);
}
