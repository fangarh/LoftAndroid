package com.dds.loftmoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabs;
    private ViewPager pager;

    private void initTabView(){
        pager = findViewById(R.id.mainActivityViewPager);

        tabs = findViewById(R.id.mainActivityTabLayout);
        tabs.removeAllTabs();

        pager.setAdapter(new BudgetPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        tabs.setupWithViewPager(pager);
        tabs.getTabAt(0).setText(R.string.creditTab);
        tabs.getTabAt(1).setText(R.string.debitTab);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabView();
    }

    //region Adapter

    static class BudgetPagerAdapter extends FragmentPagerAdapter{
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

    //endregion
}