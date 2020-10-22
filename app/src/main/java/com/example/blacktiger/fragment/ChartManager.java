package com.example.blacktiger.fragment;


import android.app.Activity;
import android.graphics.Color;
import android.util.DisplayMetrics;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;

import com.example.blacktiger.AccountApplication;
import com.example.blacktiger.db.AccountDao;
import com.example.blacktiger.entity.AccountItem;

/*
 * 图形输出的帮助类
 */
public class ChartManager {

    public Activity context;

    public ArrayList<Integer> origin_colors = new ArrayList<Integer>();

    public ChartManager(Activity context){
        this.context = context;


        origin_colors.add(Color.parseColor("#59EA3A"));
        origin_colors.add(Color.parseColor("#FFFA40"));
        origin_colors.add(Color.parseColor("#E238A7"));

        origin_colors.add(Color.parseColor("#8DB42D"));
        origin_colors.add(Color.parseColor("#3DA028"));
        origin_colors.add(Color.parseColor("#BFBC30"));
        origin_colors.add(Color.parseColor("#94256D"));

        origin_colors.add(Color.parseColor("#66C3E3"));
        origin_colors.add(Color.parseColor("#39B8E3"));
        origin_colors.add(Color.parseColor("#0095C6"));
        origin_colors.add(Color.parseColor("#257995"));
        origin_colors.add(Color.parseColor("#006181"));
    }

    public void showPieChart_Account(PieChart pieChart,String date){
        AccountApplication app = (AccountApplication)(context.getApplication());
        AccountDao dbManager = app.getDatabaseManager();

        List<AccountItem> incomeAccountList = dbManager.getOutlayStaticsList(app.currentDateMonth);

        ArrayList<String> xValues = new ArrayList<String>(); //每个饼块上的内容
        ArrayList<Entry>  yValues = new ArrayList<Entry>(); //每个饼块的实际数据
        ArrayList<Integer> colors = new ArrayList<Integer>(); // 饼图颜色

        for (int i = 0; i < incomeAccountList.size(); i++) {
            xValues.add(incomeAccountList.get(i).getCategory());
            yValues.add(new Entry((float) incomeAccountList.get(i).getMoney(), i, incomeAccountList.get(i).getCategory()));
            //colors.add(Color.parseColor("#"+accounts.get(i).hex));
            colors.add(origin_colors.get(i%origin_colors.size()));
        }

        PieDataSet pieDataSet = new PieDataSet(yValues, "支出"/*显示在比例图上*/);//y轴的集合
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离
        pieDataSet.setColors(colors);

        //设置数据
        PieData pieData = new PieData(xValues, pieDataSet);
        pieData.setValueTextSize(14f);
        pieData.setHighlightEnabled(true);
        pieChart.setData(pieData);

        //饼图显示样式
        pieChart.setBackgroundColor(Color.parseColor("#FFFFFF"));
        pieChart.setHoleColorTransparent(true);
        pieChart.setHoleRadius(50f);              //内圆半径，等于0则为实心圆
        pieChart.setTransparentCircleRadius(60f); // 半透明圈
        pieChart.setDescription("");              //描述

        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字
        pieChart.setCenterText("￥"+dbManager.getOutlaySummary(date));//饼状图中间的文字
        pieChart.setCenterTextSize(22f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(-90);        // 初始旋转角度
        pieChart.setUsePercentValues(true);  //显示成百分比
        pieChart.setRotationEnabled(false);  // 不可以手动旋转

        //设置比例图 不显示
        Legend mLegend = pieChart.getLegend();
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
        mLegend.setForm(Legend.LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);
        mLegend.setEnabled(false);//不显示比例图

        //设置动画
        pieChart.animateXY(1000, 1000);
        //pieChart.spin(2000, 0, 360, Easing.EasingOption.EaseInElastic);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度
    }
}
