package com.dds.core.dataaccess;

import android.util.Log;

import com.dds.core.DTC.AuthResponseDTC;
import com.dds.core.LoftMoney;
import com.dds.core.faces.IAuthProvider;
import com.dds.core.faces.IAuthResultProvider;
import com.dds.core.faces.IWebAuthorization;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AuthProvider implements IAuthProvider {
    // region Private fields

    private IWebAuthorization auth;
    private IAuthResultProvider resultProvider;

    // endregion

    //region Constructor

    public AuthProvider() {
        auth = LoftMoney.GetInstance().getAuthApi();
    }

    //endregion

    //region IAuthProvider


    @Override
    public void InitResultActions(IAuthResultProvider result) {
        resultProvider = result;
    }

    @Override
    public void PerformLogin(String uid) {
        auth.performLogin(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AuthResponseDTC>() {
                    @Override
                    public void accept(AuthResponseDTC s) throws Exception {
                        Log.e("TAG", "Auth " + s.getAccessToken());
                        if(resultProvider != null)
                            resultProvider.AuthResult(s, "Authorized");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(resultProvider != null)
                            resultProvider.AuthResult(null, throwable.getLocalizedMessage());
                    }
                });
    }

    //endregion
}
