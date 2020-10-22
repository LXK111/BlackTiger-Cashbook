package com.example.blacktiger.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.blacktiger.R;
import com.example.blacktiger.db.AccountDao;
import com.example.blacktiger.entity.AccountCategory;
import com.example.blacktiger.entity.AccountItem;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 收入/支出的输入界面
 */
public class AccountEditActivity extends Activity {

	private List<AccountCategory> categoryList;
	private TextView textViewSelectedType;
	private EditText editTextMoney;
	private EditText editTextRemark;
	private boolean isIncome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_edit);

		isIncome = this.getIntent().getBooleanExtra("isIncome", true);
		initView();

		textViewSelectedType = (TextView)this.findViewById(R.id.textViewSelectedType);
		editTextMoney = (EditText)this.findViewById(R.id.editTextMoney);
		editTextRemark = (EditText)this.findViewById(R.id.editTextRemark);

		textViewSelectedType.setText("工资");
		editTextMoney.setText("100");

		Button buttonOk = (Button)this.findViewById(R.id.buttonOk);
		buttonOk.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				buttonOkOnClick();

			}

		});

		editTextMoney.requestFocus();
	}

	private void initView() {
		AccountDao dbManager = new AccountDao(this);
		if (isIncome){
			categoryList = dbManager.getIncomeType();
		}else{
			categoryList = dbManager.getOutlayType();
		}

		//显示到界面
		GridView gridView = (GridView)this.findViewById(R.id.gridView1);
		//Adapter
		ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.simple_list_item_1,categoryList);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				gridViewOnItemClick(position);

			}

		});

	}

	protected void gridViewOnItemClick(int position) {
		textViewSelectedType.setText(this.categoryList.get(position).toString());

	}

	protected void buttonOkOnClick() {
		AccountItem item = new AccountItem();

		item.setCategory(textViewSelectedType.getText().toString());
		item.setRemark(editTextRemark.getText().toString());
		item.setMoney(Double.parseDouble(editTextMoney.getText().toString()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		item.setDate(sdf.format(new Date()));

		AccountDao dbManager= new AccountDao(this);
		if (isIncome){
			dbManager.addIncome(item);
		}
		else{
			dbManager.addOutlay(item);
		}
		this.setResult(1);
		this.finish();
	}

}
