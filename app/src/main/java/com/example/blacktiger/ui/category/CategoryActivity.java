package com.example.blacktiger.ui.category;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.example.blacktiger.R;


public class CategoryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        PagerAdapter sectionsPagerAdapter = new PagerAdapter(this,getSupportFragmentManager());
        ViewPager viewPager1 = findViewById(R.id.view_pager_category);
        viewPager1.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs_category);
        tabs.setupWithViewPager(viewPager1);
    }

}
