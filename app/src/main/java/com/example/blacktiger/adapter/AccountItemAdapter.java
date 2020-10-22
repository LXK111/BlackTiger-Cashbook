package com.example.blacktiger.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blacktiger.AccountApplication;
import com.example.blacktiger.R;
import com.example.blacktiger.entity.AccountItem;

import java.util.List;

/**
 * 适配器
 */
public class AccountItemAdapter extends BaseAdapter {
	private List<AccountItem> mItems;
	private LayoutInflater mInflater;
	private AccountApplication mApp;

	//构造函数
	public AccountItemAdapter(List<AccountItem> items, Activity context){
		this.mItems = items;
		mInflater = LayoutInflater.from(context);
		mApp = (AccountApplication)(context.getApplication());
	}

	@Override
	public int getCount() {   //要显示的行数
		return this.mItems.size();
	}

	@Override
	public Object getItem(int arg0) {  //某行要显示的数据
		return this.mItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {  //某行的数据ID
		return this.mItems.get(arg0).getId();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		//从布局填充得到一个view
		View view = this.mInflater.inflate(R.layout.list_view_item, null);

		//找到View上的组件
		TextView tvCategory = (TextView)view.findViewById(R.id.textViewCategory);
		TextView tvRemark = (TextView)view.findViewById(R.id.textViewRemark);
		TextView tvMoney = (TextView)view.findViewById(R.id.textViewMoney);
		TextView tvDate = (TextView)view.findViewById(R.id.textViewDate);
		ImageView imageView = (ImageView)view.findViewById(R.id.imageViewIcon);

		//把数据设置到对应的组件
		AccountItem item = this.mItems.get(arg0);
		tvCategory.setText(item.getCategory());
		tvRemark.setText(item.getRemark());
		tvMoney.setText(String.valueOf(item.getMoney()));
		tvDate.setText(item.getDate());
		int icon = mApp.accountCategoryUtil.getIncomeCategoryIcon(item.getCategory());
		if (icon>0){
			imageView.setImageResource(icon);
		}
		return view;
	}

}
