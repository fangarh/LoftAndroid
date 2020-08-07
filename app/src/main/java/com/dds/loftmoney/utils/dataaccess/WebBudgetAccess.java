package com.dds.loftmoney.utils.dataaccess;


import android.util.Log;

import com.dds.loftmoney.LoftMoney;
import com.dds.loftmoney.domain.dtc.AnswerDTC;
import com.dds.loftmoney.domain.dtc.BudgetDTC;
import com.dds.loftmoney.domain.objects.Budget;
import com.dds.loftmoney.utils.faces.IBudgetAccess;
import com.dds.loftmoney.utils.faces.IViewFeedback;
import com.dds.loftmoney.utils.faces.IWebMoneyApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WebBudgetAccess implements IBudgetAccess {
    //region private members declaration
    private LoftMoney app = LoftMoney.GetInstance();
    private List<Budget> budgets = new ArrayList<>();
    private CompositeDisposable disposer = new CompositeDisposable();
    private IViewFeedback feedback = null;
    IWebMoneyApi webApi = null;
    //endregion

    public WebBudgetAccess() {
        webApi = app.getMoneyApi();
    }

    //region IBudgetAccess implementation

    @Override
    public List<Budget> getBudget() {
        return this.budgets;
    }

    @Override
    public Budget get(Integer position) {
        return this.budgets.get(position);
    }

    @Override
    public Integer size() {
        return this.budgets.size();
    }

    @Override
    public void fill(Boolean debit, String token) {
        fillBudgetData(debit?"income":"expense", token);

    }

    //endregion
    //region test data fill
    private void fillBudgetData(String type, String token){
        Disposable dispose = webApi.getBudget(token, type)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BudgetDTC>>() {
                                @Override
                               public void accept(List<BudgetDTC> moneyList) throws Exception {
                                   budgets.clear();
                                   for(BudgetDTC money:moneyList)
                                       budgets.add(money.toBudget());

                                   Collections.sort(budgets);
                                   if(feedback != null) {
                                       feedback.DataUpdated();
                                       feedback.showMessage("Данные загружены");
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e("Exception", throwable.getMessage());
                                if(feedback != null) {
                                    feedback.DataUpdated();
                                    feedback.showMessage(throwable.getMessage());
                                }
                            }
                        });

        disposer.add(dispose);
    }

    @Override
    public void updateRow(Integer rowId, Budget budgetRow) {

    }

    @Override
    public void addBudget(final Budget budget, Boolean debit, String token) {
        String type = debit?"income":"expense";

        Disposable dispose = webApi.addBudget(token, budget.getPrice(), budget.getName(), type)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AnswerDTC>() {
                    @Override
                    public void accept(AnswerDTC result) throws Exception {
                        budget.setId(result.getId());

                        budgets.add(budget);

                        if(feedback != null){
                            feedback.DataUpdated();
                            feedback.showMessage("Данные добавлены");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Exception", throwable.getMessage());
                        if(feedback != null) {
                            feedback.DataUpdated();
                            feedback.showMessage(throwable.getMessage());
                        }
                    }
                });
        disposer.add(dispose);
    }

    @Override
    public void deleteRow(Integer rowId, String token) {

    }

    @Override
    public void InitFeedback(IViewFeedback feedback) {
        this.feedback = feedback;
    }

    //endregion
}
