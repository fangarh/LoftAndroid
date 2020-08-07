package com.dds.loftmoney.utils.faces;


import com.dds.loftmoney.domain.dtc.AuthResponseDTC;

public interface IAuthResultProvider {
    void AuthResult(AuthResponseDTC result, String message);
}
