package com.dds.loftmoney;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
        Log.e("!!!!", ">>>" + position);
        return new BudgetFragment(position == 1);

    }

    @Override
    public int getCount() {
        return 2;
    }

    //endregion
}
