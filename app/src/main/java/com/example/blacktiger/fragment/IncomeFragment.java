package com.example.blacktiger.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.blacktiger.AccountApplication;
import com.example.blacktiger.R;
import com.example.blacktiger.activity.AccountEditActivity;
import com.example.blacktiger.adapter.AccountItemAdapter;
import com.example.blacktiger.db.AccountDao;
import com.example.blacktiger.entity.AccountItem;

import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {
    View rootView;

    public IncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_income, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        Button buttonAdd = (Button)rootView.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                buttonAddOnClick();
            }

        });

        ListView listView = (ListView)rootView.findViewById(R.id.listView1);
        listView.setOnItemLongClickListener(new OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                deleteItem(id);
                return true;
            }

        });

        refreshData();
    }
    protected void deleteItem(final long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(R.string.delete_confirm_title);
        builder.setMessage(R.string.delete_confirm_msg);

        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AccountApplication app = (AccountApplication)(Objects.requireNonNull(IncomeFragment.this.getActivity()).getApplication());
                AccountDao dbManager = app.getDatabaseManager();
                dbManager.deleteIncome(id);
                refreshData();
            }
        });
        builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();


    }

    private void refreshData() {
        AccountApplication app = (AccountApplication)(Objects.requireNonNull(this.getActivity()).getApplication());
        AccountDao dbManager = app.getDatabaseManager();
        List<AccountItem> incomeAccountList = dbManager.getIncomeList();

        AccountItemAdapter adapter = new AccountItemAdapter(incomeAccountList,getActivity());
        ListView listView = (ListView)rootView.findViewById(R.id.listView1);
        listView.setAdapter(adapter);

        TextView textViewIncomeSummary = (TextView)rootView.findViewById(R.id.textViewIncomeSummary);
        textViewIncomeSummary.setText(String.valueOf(dbManager.getIncomeSummary(app.currentDateMonth)));
    }

    protected void buttonAddOnClick() {
        Intent intent =new Intent(this.getActivity(), AccountEditActivity.class);
        intent.putExtra("isIncome", true);
        this.startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("tinyaccount","onActivityResult");
        refreshData();
    }
}
