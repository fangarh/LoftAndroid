package com.dds.loftmoney.ux.activity.displaybudget;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import com.dds.loftmoney.ux.activity.addbudget.AddItemActivity;
import com.dds.loftmoney.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import static com.dds.loftmoney.ux.fragments.BudgetFragment.ADD_ITEM_ACTIVITY_REQUEST_CODE;

public class MainActivity extends AppCompatActivity {

    //region private members declaration

    public static final String TOKEN =  "AUTH_TOKEN_FOR_APP";
    private TabLayout tabs;
    private Toolbar toolbar;
    private FloatingActionButton addBtn;
    private ViewPager pager;

    //endregion

    //region overrided members

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabView();

    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        super.onActionModeStarted(mode);
        tabs.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_grey_blue));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_grey_blue));
        addBtn.setVisibility(View.GONE);
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);
        tabs.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        addBtn.setVisibility(View.VISIBLE);
    }

    //endregion

    //region private logic

    private void initTabView(){
        pager = findViewById(R.id.mainActivityViewPager);
        addBtn = findViewById(R.id.mainActivityAddBudgetRow);
        tabs = findViewById(R.id.mainActivityTabLayout);
        toolbar = findViewById(R.id.mainActivityToolBar);
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
                overridePendingTransition(R.anim.slide_in, R.anim.fade_alpha_anim);
            }
        });

        tabs.setupWithViewPager(pager);
        tabs.getTabAt(0).setText(R.string.creditTab);
        tabs.getTabAt(1).setText(R.string.debitTab);
    }

    //endregion

}