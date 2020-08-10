package com.dds.loftmoney.ux.activity.displaybudget;

import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dds.loftmoney.domain.objects.Budget;
import com.dds.loftmoney.utils.faces.presenters.IBudgetAccess;
import com.dds.loftmoney.utils.logic.WebBudgetAccess;
import com.dds.loftmoney.ux.fragments.BalanceFragment;
import com.dds.loftmoney.ux.fragments.BudgetFragment;

public class BudgetPagerAdapter extends FragmentPagerAdapter {
    //region private members

    private IBudgetAccess debit, credit;

    //endregion

    //region ctor...

    public BudgetPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        initDataAdapters();
    }

    //endregion

    //region nested members

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new BudgetFragment(false, credit, "id_cred");
            case 1:
                return new BudgetFragment(true, debit, "id_deb");
            default:
                return new BalanceFragment(debit, credit);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    //endregion

    //region Init data adapters

    private void initDataAdapters(){
        debit = new WebBudgetAccess(true);
        credit = new WebBudgetAccess(false);
    }

    //endregion
}
