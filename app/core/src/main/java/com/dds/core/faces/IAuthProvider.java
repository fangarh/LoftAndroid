package com.dds.core.faces;

public interface IAuthProvider {
    void InitResultActions(IAuthResultProvider result);
    void PerformLogin(String uid);
}
