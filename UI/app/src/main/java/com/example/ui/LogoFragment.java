package com.example.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LogoFragment extends Fragment {
    private RecyclerView recyclerView; //定义ReclerView控件
    private View view;//定义view来设置fragment中的layout
    private ArrayList<bill> bills = new ArrayList<bill>();
    private LinearAdapter LinearAdapter;
    private ImageButton mBtnAdd;

    private String content;
    public LogoFragment(String content){
        this.content = content;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_logo,container,false);
        //在mainactivity中显示所需的标题
        TextView txt_content = view.findViewById(R.id.txt_content);
        txt_content.setText(content);

        mBtnAdd = view.findViewById(R.id.ibtn_add);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddBillActivity.class);
                startActivity(intent);
            }
        });
        initRecyclerView();
        initData();
        return view;

    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_main);
        LinearAdapter = new LinearAdapter(getActivity(),bills);
        recyclerView.setAdapter(LinearAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        LinearAdapter.setOnItemClickListener(new LinearAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, bill data) {
                Toast.makeText(getActivity(),"无", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //
    private void initData(){
        for (int i=0;i<20;i++){
            //bill bill_1 = new bill();
            bill bill_2 = new bill("餐饮","三餐","200.00");
            //bill_1.getFirst_type("餐饮");
            //bill_1.getSecond_type("三餐");
            //bill_1.getMoney_cost(300);
            bills.add(bill_2);

        }
    }


}