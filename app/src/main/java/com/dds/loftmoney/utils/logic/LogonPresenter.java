package com.dds.loftmoney.utils.logic;

import android.util.Log;


import com.dds.loftmoney.LoftMoney;
import com.dds.loftmoney.domain.dtc.AuthResponseDTC;
import com.dds.loftmoney.utils.faces.presenters.ILogonPresenter;
import com.dds.loftmoney.utils.faces.views.ILogonView;
import com.dds.loftmoney.utils.faces.web.IWebAuthorization;

import java.nio.channels.NotYetBoundException;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LogonPresenter implements ILogonPresenter {
    //region private members

    private IWebAuthorization auth;
    private CompositeDisposable disposer = new CompositeDisposable();
    private ILogonView view;

    //endregion

    //region ctor...

    public LogonPresenter() {
        auth = LoftMoney.GetInstance().getAuthApi();
    }

    //endregion

    //region ILogonPresenter implementation
    @Override
    public void setView(ILogonView view) {
        this.view = view;
    }

    @Override
    public void performLogin() {
        if(view == null)
            throw new NotYetBoundException();

        view.toggleSending(true);

        String login = "dds" + (new Random()).nextInt();

        disposer.add(auth.performLogin(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AuthResponseDTC>() {
                    @Override
                    public void accept(AuthResponseDTC s) throws Exception {
                        Log.e("TAG", "Auth " + s.getAccessToken());
                        view.toggleSending(false);
                        view.successLogon(s.getAccessToken());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.toggleSending(false);
                        view.showMessage("EXCEPTION: " + throwable.getMessage());
                    }
                }));

    }
    //endregion
}
