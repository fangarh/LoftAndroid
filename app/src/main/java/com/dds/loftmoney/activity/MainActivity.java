package com.dds.loftmoney.activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dds.loftmoney.adapters.BudgetPagerAdapter;
import com.dds.loftmoney.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import static com.dds.loftmoney.fragments.BudgetFragment.ADD_ITEM_ACTIVITY_REQUEST_CODE;

public class MainActivity extends AppCompatActivity {
    public static final String TOKEN =  "AUTH_TOKEN_FOR_APP";
    //region private members declaration

    private TabLayout tabs;
    private FloatingActionButton addBtn;
    private ViewPager pager;

    //endregion

    //region private logic

    private void initTabView(){
        pager = findViewById(R.id.mainActivityViewPager);
        addBtn = findViewById(R.id.mainActivityAddBudgetRow);
        tabs = findViewById(R.id.mainActivityTabLayout);
        tabs.removeAllTabs();

        pager.setAdapter(new BudgetPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int curPage = pager.getCurrentItem();
                Fragment active = getSupportFragmentManager().getFragments().get(curPage);
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);

                intent.putExtra("color", curPage == 0 ? R.color.creditColor: R.color.debitColor);

                active.startActivityForResult(intent, ADD_ITEM_ACTIVITY_REQUEST_CODE);
            }
        });

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