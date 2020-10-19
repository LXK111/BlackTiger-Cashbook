package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_homelogo, txt_homestatistics, txt_homechart, txt_homeme;
    private FrameLayout ly_content;

    private LogoFragment fg1;
    private StatisticsFragment fg2;
    private ChartFragment fg3;
    private MeFragment fg4;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题框
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        fManager = getSupportFragmentManager();
        bindViews();
        txt_homelogo.performClick(); //默认进入主页
    }


    private void bindViews() {
        txt_homelogo = findViewById(R.id.txt_home_logo);
        txt_homestatistics = findViewById(R.id.txt_home_statistics);
        txt_homechart = findViewById(R.id.txt_home_chart);
        txt_homeme = findViewById(R.id.txt_home_me);
        ly_content = findViewById(R.id.ly_content);

        txt_homelogo.setOnClickListener(this);
        txt_homestatistics.setOnClickListener(this);
        txt_homechart.setOnClickListener(this);
        txt_homeme.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    private void setSelected() {
        txt_homelogo.setSelected(false);
        txt_homestatistics.setSelected(false);
        txt_homechart.setSelected(false);
        txt_homeme.setSelected(false);
    }

    //隐藏所有的Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) fragmentTransaction.hide(fg1);
        if (fg2 != null) fragmentTransaction.hide(fg2);
        if (fg3 != null) fragmentTransaction.hide(fg3);
        if (fg4 != null) fragmentTransaction.hide(fg4);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);

        switch (v.getId()) {
            case R.id.txt_home_logo:
                setSelected();
                txt_homelogo.setSelected(true);
                if (fg1 == null) {
                    fg1 = new LogoFragment("黑虎记账");
                    fTransaction.add(R.id.ly_content, fg1);
                } else {
                    fTransaction.show(fg1);
                }
                break;
            case R.id.txt_home_statistics:
                setSelected();
                txt_homestatistics.setSelected(true);
                if (fg2 == null) {
                    fg2 = new StatisticsFragment("统计");
                    fTransaction.add(R.id.ly_content, fg2);
                } else {
                    fTransaction.show(fg2);
                }
                break;
            case R.id.txt_home_chart:
                setSelected();
                txt_homechart.setSelected(true);
                if (fg3 == null) {
                    fg3 = new ChartFragment("图表");
                    fTransaction.add(R.id.ly_content, fg3);
                } else {
                    fTransaction.show(fg3);
                }
                break;
            case R.id.txt_home_me:
                setSelected();
                txt_homeme.setSelected(true);
                if (fg4 == null) {
                    fg4 = new MeFragment("我的");
                    fTransaction.add(R.id.ly_content, fg4);
                } else {
                    fTransaction.show(fg4);
                }
                break;

        }
        fTransaction.commit();
    }
}
