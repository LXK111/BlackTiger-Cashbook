package com.example.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.LinearViewHolder>{

    private OnItemClickListener onItemClickListener;
    private Context context;
    private ArrayList<bill> bills;

    public LinearAdapter(Context context, ArrayList<bill> bills){
        this.context = context;
        this.bills = bills;
    }


    @Override
    public LinearAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.activity_layout_linear_item,null);
        return new LinearViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LinearAdapter.LinearViewHolder holder, int position) {
        bill data = bills.get(position);
        holder.mTv1.setText(data.getFirst_type());
        holder.mTv2.setText(data.getSecond_type());
        holder.mTvMoney.setText(data.getMoney_cost());
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {

        //定义控件
        private TextView mTv1,mTv2;
        private TextView mTvMoney;

        public LinearViewHolder( View itemView) {
            super(itemView);
            //找到控件
            mTv1 = itemView.findViewById(R.id.tv_1);
            mTv2 = itemView.findViewById(R.id.tv_2);
            mTvMoney = itemView.findViewById(R.id.tv_money);


            //设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemClickListener!=null){

                        onItemClickListener.OnItemClick(v,bills.get(getLayoutPosition()));
                    }
                }
            });

        }
    }

    //设置点击事件监听器
    public interface OnItemClickListener {
        public void OnItemClick(View view, bill data);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}

