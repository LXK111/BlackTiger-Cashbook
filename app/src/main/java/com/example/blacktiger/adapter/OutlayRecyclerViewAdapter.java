package com.example.blacktiger.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.blacktiger.AccountApplication;
import com.example.blacktiger.R;
import com.example.blacktiger.entity.AccountItem;

import java.util.List;

public class OutlayRecyclerViewAdapter extends RecyclerView.Adapter<OutlayRecyclerViewAdapter.NormalTextViewHolder> {
        private final LayoutInflater mLayoutInflater;
        private List<AccountItem> mItems;
        private AccountApplication mApp;

        public OutlayRecyclerViewAdapter(Activity context, List<AccountItem> items) {
            mLayoutInflater = LayoutInflater.from(context);
            mItems = items;
            mApp = (AccountApplication)(context.getApplication());
        }

        @Override
        public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.recyclerview_item, parent, false));
        }

        @Override
        public void onBindViewHolder(NormalTextViewHolder holder, int position) {
            //把数据设置到对应的组件
            AccountItem item = this.mItems.get(position);
            holder.tvCategory.setText(item.getCategory());
            holder.tvRemark.setText(item.getRemark());
            holder.tvMoney.setText(String.valueOf(item.getMoney()));
            holder.tvDate.setText(item.getDate());
            int icon = mApp.accountCategoryUtil.getOutlayCategoryIcon(item.getCategory());
            if (icon>0){
                holder.imageView.setImageResource(icon);
            }
        }

        @Override
        public int getItemCount() {
            return mItems == null ? 0 : mItems.size();
        }

        public static class NormalTextViewHolder extends RecyclerView.ViewHolder {

            TextView tvCategory;
            TextView tvRemark;
            TextView tvMoney;
            TextView tvDate;
            ImageView imageView;

            NormalTextViewHolder(View view) {
                super(view);
                tvCategory = (TextView)view.findViewById(R.id.textViewCategory);
                tvRemark = (TextView)view.findViewById(R.id.textViewRemark);
                tvMoney = (TextView)view.findViewById(R.id.textViewMoney);
                tvDate = (TextView)view.findViewById(R.id.textViewDate);
                imageView = (ImageView)view.findViewById(R.id.imageViewIcon);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("NormalTextViewHolder", "onClick--> position = " + getPosition());
                    }
                });
            }
    }
}
