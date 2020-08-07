package com.dds.loftmoney.ux.activity.displaybudget;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dds.loftmoney.ux.activity.fragments.BudgetFragment;

public class BudgetPagerAdapter extends FragmentPagerAdapter {
    //region ctor...

    public BudgetPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    //endregion

    //region nested members

    @NonNull
    @Override
    public Fragment getItem(int position) {
        boolean isDebitFragment = position == 1;
        return new BudgetFragment(isDebitFragment);
    }

    @Override
    public int getCount() {
        return 2;
    }

    //endregion
}
