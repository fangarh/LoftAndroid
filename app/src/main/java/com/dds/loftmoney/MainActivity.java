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
    //region private members declaration

    private TabLayout tabs;
    private ViewPager pager;

    //endregion

    //region private logic

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

    //endregion

    //region overrided members

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabView();
    }

    //endregion
}