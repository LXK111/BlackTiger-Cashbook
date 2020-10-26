package com.example.blacktiger.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blacktiger.data.Entity.Blacktiger;
import com.google.gson.Gson;
import com.example.blacktiger.R;
import com.example.blacktiger.ui.detail.EditFragment;
import com.example.blacktiger.utils.DateToLongUtils;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BlacktigerAdapter extends RecyclerView.Adapter<BlacktigerAdapter.MyViewHolder> {
    private List<Blacktiger> allBlacktiger = new ArrayList<>();
    private DecimalFormat amountFormat = new DecimalFormat("#.##");
    private Context context;
    private boolean isSetOnClickListener;
    public BlacktigerAdapter(Context context, boolean isSetOnClickListener) {
        this.context=context;
        this.isSetOnClickListener=isSetOnClickListener;

    }

    public void setAllBlacktiger(List<Blacktiger> allBlacktiger) {
        this.allBlacktiger = allBlacktiger;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.blacktiger_card,parent,false);

        final MyViewHolder holder = new MyViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Blacktiger blacktiger = allBlacktiger.get(position);

        holder.tv_type.setText(blacktiger.getCategory());
        holder.imageView.setImageDrawable(context.getDrawable(getDrawableId(blacktiger.getIcon())));
        holder.tv_date.setText(DateToLongUtils.longToDate(blacktiger.getTime()));
        if(blacktiger.isType())
            holder.tv_amount.setText("- "+amountFormat.format(blacktiger.getAmount()));
        else holder.tv_amount.setText(amountFormat.format(blacktiger.getAmount()));
        if(isSetOnClickListener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    Gson gson = new Gson();
                    String wasteBookJson = gson.toJson(blacktiger, Blacktiger.class);
                    bundle.putString(EditFragment.BLACKTIGER_EDIT, wasteBookJson);
                    Navigation.findNavController(v).navigate(R.id.action_navigation_detail_to_editFragment, bundle);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return allBlacktiger.size();
    }

    //自定义ViewHolder:内部类，static 防止内存泄露
    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_type,tv_date,tv_amount;
        ImageView imageView;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_type=itemView.findViewById(R.id.blacktiger_type);
            tv_date=itemView.findViewById(R.id.blacktiger_date);
            tv_amount=itemView.findViewById(R.id.blacktiger_amount);
            imageView=itemView.findViewById(R.id.imageView_blacktiger_category);
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            clickListener.onItemClick(getAdapterPosition(),v);
//        }

    }

//    public void setOnItemClickListener(WasteBookClickListener clickListener){
//        this.clickListener=clickListener;
//    }
//
//    //点击事件接口
//    public interface WasteBookClickListener {
//        void onItemClick(int position, View v);
//        //void onItemLongClick(int position, View v);
//    }

    public static int getDrawableId(String iconName){
        Field field = null;
        int res_ID;
        try {
            field = R.drawable.class.getField(iconName);
            res_ID = field.getInt(field.getName());
        } catch (NoSuchFieldException e) {
            res_ID = R.drawable.ic_category_out_1;
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            res_ID = R.drawable.ic_category_out_1;
            e.printStackTrace();
        }
        return res_ID;
    }

}
