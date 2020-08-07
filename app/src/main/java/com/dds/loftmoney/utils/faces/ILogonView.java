package com.dds.loftmoney.utils.faces;

public interface ILogonView {
    void toggleSending(boolean isActive);
    void showMessage(String message);
    void successLogon(String token);
}
