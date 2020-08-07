package com.dds.loftmoney.utils.dataaccess;

import android.util.Log;

import com.dds.loftmoney.LoftMoney;
import com.dds.loftmoney.domain.dtc.AuthResponseDTC;
import com.dds.loftmoney.utils.faces.IAuthProvider;
import com.dds.loftmoney.utils.faces.IAuthResultProvider;
import com.dds.loftmoney.utils.faces.IWebAuthorization;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
        Disposable d = auth.performLogin(uid)
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

