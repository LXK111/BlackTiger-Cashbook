package com.example.blacktiger.fragment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blacktiger.AccountApplication;
import com.example.blacktiger.R;
import com.example.blacktiger.adapter.OutlayRecyclerViewAdapter;
import com.example.blacktiger.db.AccountDao;
import com.example.blacktiger.entity.AccountCategory;
import com.example.blacktiger.entity.AccountItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class OutlayFragment extends Fragment {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private AccountDao dbManager;
    private AccountApplication app;

    public OutlayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_outlay, container, false);
        initView();
        return mRootView;
    }

    private void initView() {
        app = (AccountApplication)(Objects.requireNonNull(this.getActivity()).getApplication());
        dbManager = app.getDatabaseManager();
        refresh();

        Button buttonAdd = (Button)mRootView.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                buttonAddOnClick();
            }

        });

    }

    private void refresh(){
        List<AccountItem> outlayAccountList = dbManager.getOutlayList();

        mRecyclerView = (RecyclerView)mRootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setAdapter(new OutlayRecyclerViewAdapter(this.getActivity(),outlayAccountList));

        TextView textViewIncomeSummary = (TextView)mRootView.findViewById(R.id.textViewIncomeSummary);
        textViewIncomeSummary.setText(String.valueOf(dbManager.getOutlaySummary(app.currentDateMonth)));
    }

    private void buttonAddOnClick() {
        initDialog();
        builder.show();
    }

    private AlertDialog.Builder builder;
    private View   dialog;
    private String[] mFrom = { "icon", "title" };
    private int[] mTo = { R.id.imageViewCategory, R.id.textViewCategory };
    private EditText editTextMoney;
    private EditText editTextRemark;
    private TextView textViewSelectedType;
    private List<AccountCategory> outlayCategoryList;
    @SuppressLint("InflateParams")
    private void initDialog() {
        builder = new AlertDialog.Builder(Objects.requireNonNull(this.getActivity()));
        //builder.setTitle(R.string.input_category);
        LayoutInflater inflater = getLayoutInflater();
        dialog = inflater.inflate(R.layout.account_edit,null);
        editTextMoney = (EditText)dialog.findViewById(R.id.editTextMoney);
        editTextMoney.requestFocus();
        textViewSelectedType = (TextView)dialog.findViewById(R.id.textViewSelectedType);
        textViewSelectedType.setText("食物");
        editTextRemark = (EditText)dialog.findViewById(R.id.editTextRemark);

        initGridView();
        builder.setView(dialog);

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AccountItem item = new AccountItem();

                item.setCategory(textViewSelectedType.getText().toString());
                item.setRemark(editTextRemark.getText().toString());
                item.setMoney(Double.parseDouble(editTextMoney.getText().toString()));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                item.setDate(sdf.format(new Date()));
                dbManager.addOutlay(item);
                refresh();
            }
        });
        builder.setNegativeButton("放弃", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

    }

    private void initGridView() {
        GridView gridView = (GridView)dialog.findViewById(R.id.gridView1);
        //Adapter
        outlayCategoryList = dbManager.getOutlayType();
        List<Map<String, Object>> outlayList = new ArrayList<>();
        for (AccountCategory c: outlayCategoryList){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", c.getIcon());
            map.put("title", c.getCategory());
            outlayList.add(map);
        }

        SimpleAdapter adapter2 = new SimpleAdapter(this.getActivity(), outlayList,
                R.layout.category_item, mFrom, mTo);
        gridView.setAdapter(adapter2);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                textViewSelectedType.setText(outlayCategoryList.get(position).getCategory());
            }
        });
    }

}
