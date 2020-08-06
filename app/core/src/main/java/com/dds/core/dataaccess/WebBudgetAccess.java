package com.dds.core.dataaccess;

import android.app.Application;
import android.util.Log;

import com.dds.core.DTC.AnswerDTC;
import com.dds.core.DTC.BudgetDTC;
import com.dds.core.DTC.MoneyDTC;
import com.dds.core.LoftMoney;
import com.dds.core.faces.IBudgetAccess;
import com.dds.core.faces.IViewFeedback;
import com.dds.core.faces.IWebMoneyApi;
import com.dds.objects.Budget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
    public void fill(Boolean debit) {
        fillBudgetData(debit?"income":"expense");

    }

    //endregion
    //region test data fill
    private void fillBudgetData(String type){
        Disposable dispose = webApi.getBudget(type)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoneyDTC>() {
                    @Override
                    public void accept(MoneyDTC moneyDTC) throws Exception {
                        budgets.clear();
                        for(BudgetDTC money:moneyDTC.getBudgetItems())
                            budgets.add(money.toBudget());

                        Log.e("loaded", "" + moneyDTC.getBudgetItems().size());
                        Collections.sort(budgets);
                        if(feedback != null) {
                            feedback.DataUpdated();
                            feedback.showMessage("Данные загружены");
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
    public void updateRow(Integer rowId, Budget budgetRow) {

    }

    @Override
    public void addBudget(final Budget budget, Boolean debit) {
        String type = debit?"income":"expense";

        Disposable dispose = webApi.addBudget(budget.getPrice(), budget.getName(), type)
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
    public void deleteRow(Integer rowId) {

    }

    @Override
    public void InitFeedback(IViewFeedback feedback) {
        this.feedback = feedback;
    }

    //endregion
}
