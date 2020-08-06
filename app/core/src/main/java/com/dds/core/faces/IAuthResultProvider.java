package com.dds.core.faces;

import com.dds.core.DTC.AuthResponseDTC;

public interface IAuthResultProvider {
     void AuthResult(AuthResponseDTC result, String message);
}
