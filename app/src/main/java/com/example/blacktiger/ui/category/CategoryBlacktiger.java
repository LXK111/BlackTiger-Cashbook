package com.example.blacktiger.ui.category;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blacktiger.R;
import com.example.blacktiger.adapters.BlacktigerAdapter;
import com.example.blacktiger.data.Entity.Blacktiger;
import com.example.blacktiger.ui.detail.DetailViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;





public class CategoryBlacktiger extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "CategoryBlacktiger";

    // TODO: Rename and change types of parameters
    private String categoryBlacktiger;
    private TextView tv_categoryWasteBook;
    private TextView tv_category_mount_wb;
    private ImageView imageViewBack;

    private RecyclerView recyclerView;
    private BlacktigerAdapter blacktigerAdapter;
    private List<Blacktiger> selectedBlacktigers;
    private DetailViewModel detailViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_category);
        if (getIntent() != null) {
            categoryBlacktiger = getIntent().getStringExtra(ARG_PARAM1);
        }
        tv_categoryWasteBook = findViewById(R.id.textView_category_wb_total);
        tv_category_mount_wb = findViewById(R.id.textView_category_mount_wb);
        imageViewBack = findViewById(R.id.imageView_wb_back);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        recyclerView = findViewById(R.id.recyclerView_categoryWb);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        blacktigerAdapter = new BlacktigerAdapter(this, false);
        recyclerView.setAdapter(blacktigerAdapter);

        detailViewModel.getAllBlacktigerLive().observe(this, new Observer<List<Blacktiger>>() {
            @Override
            public void onChanged(List<Blacktiger> blacktigers) {
                if (blacktigers != null) {
                    DecimalFormat mAmountFormat = new DecimalFormat("0.00");
                    int mount = 0;
                    double total = 0.0;
                    selectedBlacktigers = new ArrayList<>();
                    for (Blacktiger w : blacktigers) {
                        if (w.getCategory().equals(categoryBlacktiger)) {
                            selectedBlacktigers.add(w);
                            total += w.getAmount();
                            mount++;
                        }
                    }
                    tv_category_mount_wb.setText("总计：" + mount + "条账单");
                    tv_categoryWasteBook.setText("共 " + mAmountFormat.format(total) + "元");
                    blacktigerAdapter.setAllBlacktiger(selectedBlacktigers);
                    blacktigerAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
