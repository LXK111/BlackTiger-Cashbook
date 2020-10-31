package com.example.blacktiger.ui.chart;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.ObservableField;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.blacktiger.R;
import com.example.blacktiger.adapters.BlacktigerAdapter;
import com.example.blacktiger.data.Entity.Blacktiger;
import com.example.blacktiger.utils.DateToLongUtils;
import com.example.blacktiger.utils.PieChartUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ChartFragment extends Fragment {

    private boolean isOUT = true;
    private Double IN = 0.0, OUT = 0.0, TOTAL = 0.0, account_in = 0.0, account_out = 0.0, account_total = 0.0;
    private long start, end;
    private String selectedStr = "近1个月";
    private String account_name = "全部";
    private List<Blacktiger> allBlacktigers;
    private MutableLiveData<List<Blacktiger>> selectedBlacktiger = new MutableLiveData<>();

    private RecyclerView recyclerView;
    private BlacktigerAdapter blacktigerAdapter;
    private LiveData<List<Blacktiger>> blacktigersLive;
    private ChartViewModel chartViewModel;
    private LineChart lineChart;
    private HashMap dataMap;
    private PieChart mPieChart;
    private Button bt_OUT, bt_IN , bt_SCS_User , bt_SCS_Account;
    private TextView select;
    public String get_scs_user = "全部";
    public String get_scs_account = "全部";

    private TextView Acc_name, Acc_total;

    private ObservableField<String> mMembers = new ObservableField<>();


    private OptionsPickerView pvNoLinkOptions;
    private ArrayList<String> options1Items_type = new ArrayList<>(), options1Items_INOUT = new ArrayList<>();
    private ArrayList<ArrayList<String>> options1Items_date = new ArrayList<>();

    private static final String TAG = "ChartFragment";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chart, container, false);
        chartViewModel = ViewModelProviders.of(this).get(ChartViewModel.class);
        bt_IN = root.findViewById(R.id.textView_in_chart);
        bt_OUT = root.findViewById(R.id.textView_out_chart);
        bt_SCS_User = root.findViewById(R.id.textView_scs_user);
        bt_SCS_Account = root.findViewById(R.id.textView_scs_account);

        Acc_name = root.findViewById(R.id.account1);
        Acc_total = root.findViewById(R.id.account3);

        bt_IN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOUT = false;
                selector(selectedStr);
            }
        });
        bt_OUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOUT = true;
                selector(selectedStr);
            }
        });

        bt_SCS_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] item = new String[]{"全部","我","孩子","妻子","丈夫","父母","其他"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                builder.setTitle("按成员统计");
                builder.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Objects.requireNonNull(getActivity()), item[which], Toast.LENGTH_SHORT).show();
                        get_scs_user = new String(item[which]);
                        Log.d(TAG, get_scs_user);

                        get_scs_account = new String("全部");
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                selector(selectedStr);
            }
        });

        bt_SCS_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] item = new String[]{"全部","校园卡","平安银行","工商银行","蚂蚁花呗","信用卡","微信","支付宝"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                builder.setTitle("按成员统计");
                builder.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Objects.requireNonNull(getActivity()), item[which], Toast.LENGTH_SHORT).show();
                        get_scs_account = new String(item[which]);
                        get_scs_user = new String("全部");
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                selector(selectedStr);
            }
        });



        chartViewModel.getAllBlacktigerLive().observe(getViewLifecycleOwner(), new Observer<List<Blacktiger>>() {
            @Override
            public void onChanged(List<Blacktiger> blacktigers) {
                allBlacktigers = blacktigers;
                selector(selectedStr);
            }
        });
        //饼状图数据，更新UI
        selectedBlacktiger.observe(getViewLifecycleOwner(), new Observer<List<Blacktiger>>() {
            @Override
            public void onChanged(List<Blacktiger> blacktigers) {

                dataMap = new HashMap();
                account_in = 0.0;
                account_out = 0.0;
                if (blacktigers.isEmpty()) {
                    PieChartUtils.getPitChart().setPieChart(mPieChart, dataMap, isOUT ? "支出" : "收入", true);
                }
                if (blacktigers != null && !blacktigers.isEmpty()) {
                    List<Blacktiger> blacktigerTemp = new ArrayList<>();
                    String categoriesTmp = "";
                    double[] cWeight = new double[blacktigers.size()];
                    String[] categories = new String[blacktigers.size()];
                    int i = 0;
                    for (Blacktiger w : blacktigers) {
                        Log.d(TAG, "onChanged: ggg");
                        Log.d(TAG, get_scs_user);
                        Log.d(TAG, "onChanged: ggg");
                        if(!get_scs_user.equals("全部")) {
                            Log.d(TAG, get_scs_user);
                            String cTmp = w.getCategory();
                            if(get_scs_user.equals(w.getMembers())) {
                                if (!categoriesTmp.contains(cTmp)) {
                                    blacktigerTemp.add(w);
                                    categoriesTmp += cTmp + " ";
                                    categories[i] = cTmp;
                                    cWeight[i] += w.getAmount();
                                    i++;
                                } else {
                                    for (int j = 0; j < i; j++)
                                        if (categories[j].equals(cTmp)) {
                                            cWeight[j] += w.getAmount();
                                            continue;
                                        }
                                }
                            }
                        }else if(!get_scs_account.equals("全部")){
                            String cTmp = w.getCategory();
                            if(get_scs_account.equals(w.getAccount())) {
                                if (!categoriesTmp.contains(cTmp)) {
                                    blacktigerTemp.add(w);
                                    categoriesTmp += cTmp + " ";
                                    categories[i] = cTmp;
                                    cWeight[i] += w.getAmount();
                                    i++;
                                } else {
                                    for (int j = 0; j < i; j++)
                                        if (categories[j].equals(cTmp)) {
                                            cWeight[j] += w.getAmount();
                                            continue;
                                        }
                                }
                            }
                        }else{
                            String cTmp = w.getCategory();
                            if (!categoriesTmp.contains(cTmp)) {
                                blacktigerTemp.add(w);
                                categoriesTmp += cTmp + " ";
                                categories[i] = cTmp;
                                cWeight[i] += w.getAmount();
                                i++;
                            } else {
                                for (int j = 0; j < i; j++)
                                    if (categories[j].equals(cTmp)) {
                                        cWeight[j] += w.getAmount();
                                        continue;
                                    }
                            }
                        }
                    }
                    double sum = isOUT ? OUT : IN;
                    for (int j = 0; j < i; j++) {
                        dataMap.put(categories[j], cWeight[j] / sum * 100);
                    }
                    PieChartUtils.getPitChart().setPieChart(mPieChart, dataMap, isOUT ? "支出" : "收入", true);

                    Acc_name.setText(get_scs_account);

                    if (get_scs_account.equals("全部")) {
                        for (Blacktiger w: allBlacktigers) {
                            if (w.isType()) {
                                account_in = account_in + w.getAmount();
                            }
                            else {
                                account_out = account_out + w.getAmount();
                            }
                        }
                    }
                    else {
                        for (Blacktiger w: allBlacktigers) {
                            if (w.getAccount().equals(get_scs_account)) {
                                if (w.isType()) {
                                    account_in = account_in + w.getAmount();
                                }
                                else {
                                    account_out = account_out + w.getAmount();
                                }
                            }
                        }
                    }
                    account_total = account_out - account_in;
                    if (account_total < 0) {
                        Acc_total.setText("" + account_total);
                    }
                    else {
                        Acc_total.setText("" + account_total);
                    }

                    blacktigerAdapter.setAllBlacktiger(blacktigerTemp);
                    blacktigerAdapter.notifyDataSetChanged();
                }
            }
        });
        //选择器
        select = root.findViewById(R.id.tv_select_chart);
        //饼状图
        mPieChart = root.findViewById(R.id.mPieChart);
        //点击事件
        mPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry pieEntry = (PieEntry) e;
                mPieChart.setCenterText(pieEntry.getLabel());
            }

            @Override
            public void onNothingSelected() {

            }
        });
        return root;
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化适配器

        recyclerView = requireActivity().findViewById(R.id.recyclerView_blacktiger_chart);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        blacktigerAdapter = new BlacktigerAdapter(requireContext(), false);
        recyclerView.setAdapter(blacktigerAdapter);


        initCustomOptionPicker();
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvNoLinkOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        String temp_date = options1Items_date.get(options1).get(options2);
                        select.setText(temp_date + "▼");
                        selectedStr = temp_date;
                        selector(temp_date);
                    }
                }).setSubmitText("确定")
                        .setCancelText("取消")
                        .setTitleText("查询")
                        .setOutSideCancelable(false)
                        .build();
                pvNoLinkOptions.setPicker(options1Items_type, options1Items_date);
                pvNoLinkOptions.show();
            }
        });
    }

    //二级联动
    private void initCustomOptionPicker() {
        //选项0
        options1Items_INOUT.add("支出");
        options1Items_INOUT.add("收入");
        //选项1
        options1Items_type.add("按周");
        options1Items_type.add("按月");
        options1Items_type.add("按年");
        //选项2
        ArrayList<String> item_0 = new ArrayList<>();
        item_0.add("当前周");
        item_0.add("近两周");
        item_0.add("近三周");
        ArrayList<String> item_1 = new ArrayList<>();
        item_1.add("近1个月");
        item_1.add("近3个月");
        item_1.add("近6个月");
        ArrayList<String> item_2 = new ArrayList<>();
        for (int i = 2019; i >= 2010; i--) {
            item_2.add(i + "年");
        }
        options1Items_date.add(item_0);
        options1Items_date.add(item_1);
        options1Items_date.add(item_2);
    }


    private void selector(String timeStr) {
        List<Blacktiger> showBlacktigers = new ArrayList<>();
        IN = 0.0;
        OUT = 0.0;
        if(!get_scs_user.contains("全部")){
            //支出
            if (isOUT) {
                setStartEnd(timeStr);
                if (allBlacktigers != null)
                    for (Blacktiger w : allBlacktigers) {
                        if(get_scs_user.equals(w.getMembers())){
                            if (w.isType() && w.getTime() >= end && w.getTime() <= start) {
                                showBlacktigers.add(w);
                                OUT += w.getAmount();
                            }
                        }
                    }
            } else if (!isOUT) {
                setStartEnd(timeStr);
                for (Blacktiger w : allBlacktigers) {
                    if(get_scs_user.equals(w.getMembers())){
                        if (!w.isType() && w.getTime() >= end && w.getTime() <= start) {
                            showBlacktigers.add(w);
                            IN += w.getAmount();
                        }
                    }
                }
            }
        }else if(!get_scs_account.contains("全部")){
            //支出
            if (isOUT) {
                setStartEnd(timeStr);
                if (allBlacktigers != null)
                    for (Blacktiger w : allBlacktigers) {
                        if(get_scs_account.equals(w.getAccount())){
                            if (w.isType() && w.getTime() >= end && w.getTime() <= start) {
                                showBlacktigers.add(w);
                                OUT += w.getAmount();
                            }
                        }
                    }
            } else if (!isOUT) {
                setStartEnd(timeStr);
                for (Blacktiger w : allBlacktigers) {
                    if(get_scs_account.equals(w.getAccount())){
                        if (!w.isType() && w.getTime() >= end && w.getTime() <= start) {
                            showBlacktigers.add(w);
                            IN += w.getAmount();
                        }
                    }
                }
            }
        }else{
            if (isOUT) {
                setStartEnd(timeStr);
                if (allBlacktigers != null)
                    for (Blacktiger w : allBlacktigers) {
                        if (w.isType() && w.getTime() >= end && w.getTime() <= start) {
                            showBlacktigers.add(w);
                            OUT += w.getAmount();
                        }
                    }
            } else if (!isOUT) {
                setStartEnd(timeStr);
                for (Blacktiger w : allBlacktigers) {
                    if (!w.isType() && w.getTime() >= end && w.getTime() <= start) {
                        showBlacktigers.add(w);
                        IN += w.getAmount();
                    }
                }
            }
        }
        selectedBlacktiger.setValue(showBlacktigers);
    }

    private void setStartEnd(String timeStr) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        start = cal.getTimeInMillis();
        if (timeStr.contains("周")) {
            if (timeStr.charAt(0) == '当') {
                //一周
                cal.add(Calendar.DAY_OF_MONTH, -7);
                end = cal.getTimeInMillis();
            } else if (timeStr.charAt(1) == '两') {
                //两周
                cal.add(Calendar.DAY_OF_MONTH, -14);
                end = cal.getTimeInMillis();

            } else if (timeStr.charAt(1) == '三') {
                //三周
                cal.add(Calendar.DAY_OF_MONTH, -21);
                end = cal.getTimeInMillis();
            }

        } else if (timeStr.contains("月")) {
            if (timeStr.charAt(1) == '1') {
                //一个月
                cal.add(Calendar.MONTH, -1);
                end = cal.getTimeInMillis();
            } else if (timeStr.charAt(1) == '2') {
                //2个月
                cal.add(Calendar.MONTH, -2);
                end = cal.getTimeInMillis();

            } else if (timeStr.charAt(1) == '3') {
                //3个月
                cal.add(Calendar.MONTH, -3);
                end = cal.getTimeInMillis();
            }
        } else if (timeStr.contains("年")) {
            //按年
            int yearTemp = Integer.parseInt(timeStr.substring(0, timeStr.length() - 1));
            end = DateToLongUtils.dateToLong(yearTemp + "-1-1 00:00:00");
            start = DateToLongUtils.dateToLong(yearTemp + "-12-31 23:59:59");
        }
    }

}

