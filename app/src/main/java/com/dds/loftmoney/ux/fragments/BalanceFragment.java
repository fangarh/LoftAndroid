package com.dds.loftmoney.ux.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dds.loftmoney.R;
import com.dds.loftmoney.domain.objects.Budget;
import com.dds.loftmoney.utils.faces.presenters.IBudgetAccess;
import com.dds.loftmoney.ux.activity.displaybudget.BudgetAdapter;
import com.dds.loftmoney.ux.customview.BalanceView;

import java.util.List;
import java.util.Random;


public class BalanceFragment extends Fragment {
    //region private members

    private IBudgetAccess debit, credit;
    private BalanceView balance;
    private TextView tvTotal, tvDebit, tvCredit;

    //endregion

    //region ctor...

    public BalanceFragment(IBudgetAccess debit, IBudgetAccess  credit) {
        this.debit = debit;
        this.credit = credit;
    }

    //endregion

    //region private logic

    private void Recalculate(){
        Integer debitTotal = this.debit.calcTotal();
        Integer creditTotal = this.credit.calcTotal();
        tvDebit.setText("" + debitTotal);
        tvCredit.setText("" + creditTotal);
        tvTotal.setText("" + (debitTotal-creditTotal));

        balance.initBudgetData(creditTotal, debitTotal);
    }



    private void initControls(View view){
        balance = view.findViewById(R.id.balanceView);
        tvTotal = view.findViewById(R.id.txtBalanceTotalValue);
        tvCredit = view.findViewById(R.id.balanceCredit);
        tvDebit = view.findViewById(R.id.balanceDebit);
    }

    //endregion

    //region overrided members

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_balance, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initControls(view);
        Recalculate();
    }

    @Override
    public void onResume() {
        Log.e("INFL", "INFL");
        Recalculate();
        super.onResume();
    }

    //endregion
}