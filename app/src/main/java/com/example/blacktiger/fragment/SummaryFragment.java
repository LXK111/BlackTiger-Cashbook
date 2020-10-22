package com.example.blacktiger.fragment;

import com.github.mikephil.charting.charts.PieChart;

import com.example.blacktiger.AccountApplication;
import com.example.blacktiger.R;
import com.example.blacktiger.db.AccountDao;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 概要界面
 * 用图形方式，统计支出类别
 */
public class SummaryFragment extends Fragment {

    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_summary, container,false);

        initView();

        return rootView;
    }

    private void initView() {
        //显示余额
        AccountApplication app = (AccountApplication)(this.getActivity().getApplication());
        AccountDao dbManager = app.getDatabaseManager();

        TextView textViewSummary = (TextView)rootView.findViewById(R.id.textViewSummary);

        double summary = dbManager.getIncomeSummary(app.currentDateMonth) -
                dbManager.getOutlaySummary(app.currentDateMonth);
        textViewSummary.setText(String.valueOf(summary));

        //绘制分布图
        ChartManager chartManager = new ChartManager(this.getActivity());
        PieChart pieChart = (PieChart) rootView.findViewById(R.id.pie_chart);
        chartManager.showPieChart_Account(pieChart, app.currentDateMonth);

    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

}
