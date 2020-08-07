package com.dds.loftmoney.utils.faces;


public interface IAuthProvider {
    void InitResultActions(IAuthResultProvider result);
    void PerformLogin(String uid);
}
