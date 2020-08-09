package com.dds.loftmoney.utils.logic;


import android.util.Log;

import com.dds.loftmoney.LoftMoney;
import com.dds.loftmoney.R;
import com.dds.loftmoney.domain.dtc.AnswerDTC;
import com.dds.loftmoney.domain.dtc.BudgetDTC;
import com.dds.loftmoney.domain.objects.Budget;
import com.dds.loftmoney.utils.faces.presenters.IBudgetAccess;
import com.dds.loftmoney.utils.faces.views.IViewFeedback;
import com.dds.loftmoney.utils.faces.web.IWebMoneyApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebBudgetAccess implements IBudgetAccess {
    //region private members declaration
    private LoftMoney app = LoftMoney.GetInstance();
    private List<Budget> budgets = new ArrayList<>();
    private CompositeDisposable disposer = new CompositeDisposable();
    private IViewFeedback feedback = null;
    private Integer deleteCounter;
    private boolean isDebitDeleting;

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
        fillBudgetData(debit?"income":"expense", token, false);

    }

    //endregion

    //region data fill

    private void fillBudgetData(String type, String token, final boolean silent){
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
                                       if(!silent)
                                            feedback.showMessage(R.string.data_load_compleated);
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
                            feedback.showMessage(R.string.data_added_success);
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
    public void deleteRow(String rowId, final String token) {
        Call<AnswerDTC> call = webApi.deleteBudget(token, String.valueOf(rowId));
        call.enqueue(new Callback<AnswerDTC>() {
            @Override
            public void onResponse(Call<AnswerDTC> call, Response<AnswerDTC> response) {
                if(feedback != null) {
                    afterDeleteUpdate(token);
                    //feedback.showMessage(throwable.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AnswerDTC> call, Throwable t) {

            }
        });
    }
    @Override
    public void deleteBudgetRows(List<String> ids, Boolean debit, String token) {
        deleteCounter = ids.size();
        isDebitDeleting = debit;
        for (String id:ids) {
            deleteRow(id, token);
        }
    }

    public void afterDeleteUpdate(String token){
        deleteCounter --;
        if(deleteCounter <= 0){

            fillBudgetData(isDebitDeleting?"income":"expense", token, true);

            feedback.showMessage(R.string.data_deleted_success);
        }
    }

    @Override
    public void InitFeedback(IViewFeedback feedback) {
        this.feedback = feedback;
    }

    //endregion
}
