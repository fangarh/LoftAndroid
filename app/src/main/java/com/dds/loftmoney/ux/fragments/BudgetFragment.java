package com.dds.loftmoney.ux.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dds.loftmoney.utils.logic.WebBudgetAccess;
import com.dds.loftmoney.utils.faces.presenters.IBudgetAccess;
import com.dds.loftmoney.utils.faces.views.IViewFeedback;
import com.dds.loftmoney.ux.activity.displaybudget.BudgetAdapter;
import com.dds.loftmoney.ux.activity.displaybudget.MainActivity;
import com.dds.loftmoney.ux.events.BudgetRowClickEventArgs;
import com.dds.loftmoney.ux.activity.displaybudget.BudgetRowsDividerDecorator;
import com.dds.loftmoney.ux.events.IBudgetRowClick;
import com.dds.loftmoney.R;
import com.dds.loftmoney.domain.objects.Budget;

public class BudgetFragment extends Fragment implements IViewFeedback, ActionMode.Callback {

    //region ctor...

    public BudgetFragment(boolean debit, IBudgetAccess budget, String idd) {
        isDebit = debit;
        if(!debit){
            color = R.color.creditColor;
        }
        this.idd = idd;

        budget.InitFeedback(this);

        this.budget = new BudgetAdapter(budget);
    }

    //endregion

    //region private members

    private String idd;
    private RecyclerView recyclerList;
    private Context context;
    private SwipeRefreshLayout swipeRefresh;

    private Integer color = R.color.debitColor;
    private boolean isDebit;
    private BudgetAdapter budget;
    private ActionMode currentActionMode;

    public static final int ADD_ITEM_ACTIVITY_REQUEST_CODE  = 0x000001;
    public static final int EDIT_ITEM_ACTIVITY_REQUEST_CODE = 0x000002;

    private Budget editingRow = null;
    private Integer editingRowId = -1;

    //endregion

    //region overrided members

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_budget, null);

        fillView(fragment);
        setEvents(fragment);

        recyclerList.setAdapter(budget);
        recyclerList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        RecyclerView.ItemDecoration dividerItemDecoration =
                new BudgetRowsDividerDecorator(
                        ContextCompat.getDrawable(context, R.drawable.budget_row_divider));

        recyclerList.addItemDecoration(dividerItemDecoration);

        String token = PreferenceManager
                .getDefaultSharedPreferences(getContext())
                .getString(MainActivity.TOKEN, "");

        budget.fill(color, token);

        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_ITEM_ACTIVITY_REQUEST_CODE && editingRow != null) {
            if (resultCode == Activity.RESULT_OK) {
                editingRow.setPrice(data.getStringExtra("BudgetPrice"));
                editingRow.setName(data.getStringExtra("BudgetName"));
                budget.notifyItemChanged(editingRowId);
            }
            editingRow = null;
            editingRowId = -1;
        }

        if(requestCode == ADD_ITEM_ACTIVITY_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK) {
                editingRow = new Budget();
                editingRow.setPrice(data.getStringExtra("BudgetPrice"));
                editingRow.setName(data.getStringExtra("BudgetName"));

                Log.e("type", isDebit?"deb":"cred");
                String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(MainActivity.TOKEN, "");
                budget.Add(editingRow, token);
                editingRow = null;
            }
        }
    }

    //endregion

    //region private logic

    private void fillView(View view){
        context = getContext();
        recyclerList = view.findViewById(R.id.mainActivityBudgetRecyclerList);
        swipeRefresh = view.findViewById(R.id.addItemFragmentSwipeRefresh);
    }

    private void setEvents(View view){

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(MainActivity.TOKEN, "");
                budget.fill(color, token);
            }
        });

        budget.setOnClick(new IBudgetRowClick() {
            @Override
            public void onBudgetRowClick(BudgetRowClickEventArgs e) {
                /*
                **** if will need edit
                if(!deleteMode){
                    Intent intent = new Intent(context, AddItemActivity.class);

                    intent.putExtra("BudgetName", e.getRowData().getName());
                    intent.putExtra("BudgetPrice", e.getRowData().getPrice());
                    intent.putExtra("Id", e.getRowData().getId().toString());
                    intent.putExtra("color", color);
                    startActivityForResult(intent, EDIT_ITEM_ACTIVITY_REQUEST_CODE);
                    // startActivity(intent);
                    editingRow = e.getRowData();
                    editingRowId = e.getRowId();
                }else {
                    budget.toggleSelection(e.getRowId());
                }*/
                //getActivity().startActionMode(BudgetFragment.this);
                budget.clearSelection(e.getRowId());

                if(currentActionMode != null){
                    currentActionMode.setTitle(getString(R.string.selected_count_title, String.valueOf(budget.getSelectionsCount())));
                }
            }

            @Override
            public void onBudgetRowLongClick(BudgetRowClickEventArgs e) {
                if(currentActionMode == null)
                    getActivity().startActionMode(BudgetFragment.this);

                budget.toggleSelection(e.getRowId());

                if(currentActionMode != null)
                    currentActionMode.setTitle(getString(R.string.selected_count_title, String.valueOf(budget.getSelectionsCount())));
            }
        });
    }

    //endregion



    //region ActionMode.Callback implementation

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        currentActionMode = actionMode;
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater menuInf = new MenuInflater(getActivity());
        menuInf.inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.removeItemBtn){
            new AlertDialog.Builder(getContext()).setMessage(R.string.delete_alert_msg).
                    setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(MainActivity.TOKEN, "");
                            budget.deleteSelected(token);
                            budget.clearSelections();
                            currentActionMode.setTitle(getString(R.string.selected_count_title, String.valueOf(budget.getSelectionsCount())));
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        currentActionMode = null;
        budget.clearSelections();
    }


    //endregion

    //region IViewFeedback implementation

    @Override
    public void showMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(Integer resource) {
        Toast.makeText(requireContext(), getString(resource), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void DataUpdated() {
        budget.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public String getIid() {
        return this.idd;
    }

    //endregion
}
