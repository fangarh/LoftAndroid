package com.dds.loftmoney.utils.faces.views;

public interface ILogonView {
    void toggleSending(boolean isActive);
    void showMessage(String message);
    void successLogon(String token);
}
